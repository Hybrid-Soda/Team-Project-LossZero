from fastapi import APIRouter, HTTPException, Body, Request
from pydantic import BaseModel
import requests
import httpx
router = APIRouter()

# 데이터 수신 엔드포인트
@router.post("/wifi/data")
async def receive_arduino_data(request: Request):
    data = await request.json()
    print("Received data from Arduino:", data)
    return {"status": "success", "received_data": data}

@router.post("/wifi/conveyor")
async def receive_conveyor_data(request: Request):
    data = await request.json()
    print("Received conveyor data from Arduino:", data)
    conveyor_state = data.get("conveyor_state")
    return {"status": "success", "received_data": {"conveyor_state": conveyor_state}}



# # 전역 변수 설정
# command = "off"  # 기본값으로 "off" 설정

# 모델 정의
class Command(BaseModel):
    new_command: str
    


# @router.get("/wifi/command")
# async def send_command():
#     """
#     Arduino가 주기적으로 명령을 확인하는 GET 요청 엔드포인트
#     """
#     return {"command": command}

# @router.post("/wifi/command")
# async def set_command(command_data: Command):
#     """
#     Arduino에 보낼 명령을 설정하는 엔드포인트 (예: "on" 또는 "off")
#     """
#     global command
#     new_command = command_data.new_command
#     if new_command not in ["on", "off"]:
#         raise HTTPException(status_code=400, detail="Command must be 'on' or 'off'")
#     command = new_command
#     print(f"Command set to: {command}")
#     return {"status": "success", "new_command": command}

arduino_url = "http://192.168.137.15/update_command"  # Arduino의 IP 주소

# Arduino에 명령을 전송하는 비동기 함수
async def update_arduino_command(command: str):
    async with httpx.AsyncClient() as client:
        response = await client.post(arduino_url, data={"new_command": command})
        if response.status_code == 200:
            return response.json()
        else:
            raise HTTPException(status_code=response.status_code, detail=f"Failed to update command: {response.text}")

# FastAPI 엔드포인트
@router.post("/control_arduino")
async def control_arduino(command: Command):
    """
    Arduino의 상태를 변경하는 FastAPI 엔드포인트
    """
    response = await update_arduino_command(command.new_command)
    return {"status": "success", "arduino_response": response}


# def update_arduino_command(command):
#     response = requests.post(arduino_url, data={"new_command": command})
#     if response.status_code == 200:
#         print("Command updated successfully:", response.json())
#     else:
#         print("Failed to update command:", response.text)

# # Arduino에 'on' 명령 보내기
# update_arduino_command("off")