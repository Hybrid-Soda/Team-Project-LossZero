import time
import uuid
import json
import paho.mqtt.client as mqtt
from classification import classification

unknown_count = 0
products = {}

# MQTT Client 생성하는 함수
def create_client() -> mqtt.Client:
    client = mqtt.Client(client_id=f"RaspberryPi-Client-{uuid.uuid4()}")
    client.on_connect = on_connect
    client.on_subscribe = on_subscribe
    client.on_message = on_message
    client.connect(host="k11e202.p.ssafy.io", port=1883, keepalive=60)
    client.loop_start()
    return client

# MQTT 브로커에 연결될 때 호출되는 함수
def on_connect(client: mqtt.Client, userdata, flags: dict, reason_code: int):
    connection_messages = {
        0: "Connection successful",
        1: "Connection refused - incorrect protocol version",
        2: "Connection refused - invalid client identifier",
        3: "Connection refused - server unavailable",
        4: "Connection refused - bad username or password",
        5: "Connection refused - not authorised"
    }

    if reason_code == 0:
        client.subscribe("realtime-cycle", qos=2)
    else:
        message = connection_messages.get(reason_code, "Currently unused")
        print(message)

# 구독 성공 시 호출되는 함수
def on_subscribe(client: mqtt.Client, userdata, mid: int, reason_code_list: list):
    try:
        print(f"Broker granted the following QoS: {reason_code_list[0]}")
    except:
        print("Error : Failed to subscribe")

# 메시지 수신 시 호출되는 함수
def on_message(client: mqtt.Client, userdata, message: mqtt.MQTTMessage):
    try:
        payload: str = message.payload.decode()
        data: dict[str, str] = json.loads(payload)
    except TypeError:
        print("Type error in message data")
    except json.decoder.JSONDecodeError:
        print("JSON format is incorrect")

    sender = data.get('sender')
    status = data.get('status')
    
    if sender == 'belt' and status == 'off':
        result = classification()
        update_product(result, client)


# 제품 상태를 기록
def update_product(result: str, client: mqtt.Client):
    global unknown_count
    print(f'>> result is : {result} <<')

    response = f'{{ "sender" : "camera", "status" : "{result}" }}'
    client.publish("realtime-cycle", response)

    if result == 'unknown':
        is_unknown(client)
    else:
        products[result] = products.setdefault(result, 0) + 1
        unknown_count = 0
        print(f'products = {products}')


# unknown 연속 누적 시 예외 처리
def is_unknown(client: mqtt.Client):
    global unknown_count
    unknown_count += 1

    if unknown_count >= 3:
        response = f'{{ "sender" : "camera", "status" : "error" }}'
        client.publish("realtime-cycle", response)
        unknown_count = 0


# 품질 리포트를 발행하는 함수
def publish_quality_report(client: mqtt.Client):
    print('>> send to realtime <<')
    response = f'''{{
        "sender" : "raspberry-pi",
        "lineId" : 1,
        "quality" : {{
            "normal" : {products.get("normal", 0)},
            "defective" : {products.get("defective", 0)},
            "reusable" : {products.get("reusable", 0)}
        }}
    }}'''
    client.publish("realtime-prod", response)
    products.clear()


# 메인 함수
def run():
    client = create_client()

    try:
        while True:
            time.sleep(1)
            if sum(products.values()) == 5:
                publish_quality_report(client)
    except KeyboardInterrupt:
        print("Program terminated by user")
    except Exception as e:
        print(f"Unexpected error in main loop: {e}")
    finally:
        client.loop_stop()
        client.disconnect()


if __name__ == '__main__':
    run()