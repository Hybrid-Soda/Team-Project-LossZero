from fastapi import FastAPI
from routers import wifi

from fastapi.middleware.cors import CORSMiddleware


app = FastAPI()

# 라우터 등록
app.include_router(wifi.router)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 출처 허용
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"],  # 모든 헤더 허용
)

@app.get("/")
async def root():
    return {"message": "Arduino WiFi API is running!"}


