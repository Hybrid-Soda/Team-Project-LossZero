import time
import uuid
import json
import paho.mqtt.client as mqtt

from classification import classification


# MQTT 브로커에 연결될 때 호출되는 함수
def on_connect(client, userdata, flags, reason_code):
    if reason_code:
        print(f"Failed to connect")
    else:
        client.subscribe("realtime-cycle", qos=2)


# 구독 성공 시 호출되는 함수
def on_subscribe(client, userdata, mid, reason_code_list):
    try:
        print(f"Broker granted the following QoS: {reason_code_list[0]}")
    except:
        print(f"Broker rejected your subscription")


# 메시지 수신 시 호출되는 함수
def on_message(client, userdata, message):
    global products
    try:
        data: dict[str, str] = json.loads(message.payload.decode())
        sender = data.get('sender', 'unknown')

        if sender != 'belt':
            return
        
        result = classification()

        products[result] = products.setdefault(result, 0) + 1
        response = f'{{ "sender" : "camera", "status" : "{result}" }}'
        client.publish("realtime-cycle", response)

    except json.decoder.JSONDecodeError:
        print('format is wrong')
    except TypeError:
        print('type is wrong')


# 품질 리포트를 발행하는 함수
def publish_quality_report(client, products):
    if sum(products.values()) == 10:
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
def main():
    global products
    products = {}
    client = mqtt.Client(client_id=f"RaspberryPi-Client-{uuid.uuid4()}")
    client.on_connect = on_connect
    client.on_subscribe = on_subscribe
    client.on_message = on_message
    client.connect("k11e202.p.ssafy.io", 1883, 60)
    client.loop_start()

    try:
        while True:
            time.sleep(1)
            publish_quality_report(client, products)

    except KeyboardInterrupt:
        print("Program terminated")
    finally:
        client.loop_stop()
        client.disconnect()


if __name__ == '__main__':
    main()
