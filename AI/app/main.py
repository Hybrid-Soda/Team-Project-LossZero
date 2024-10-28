from fastapi import FastAPI


app = FastAPI()

@app.get("/")
async def root():
    return {
        "message": "Hello World"
    }

# Spring에서 신호를 받음
# Spring에 실시간 품질 데이터를 보냄
@app.post("/inference")
async def inference():
    # 카메라를 켠다
    # 추론한다
    # 불량품 개수 데이터를 보낸다
    # 위치를 보낸다
    # 불량품이 없으면 완료 상태를 보낸다
    # 카메라를 끈다
    return

def main(env: str, debug: bool):
    os.environ["ENV"] = env
    os.environ["DEBUG"] = str(debug)
    uvicorn.run(
        app="app.server:app",
        host=config.APP_HOST,
        port=config.APP_PORT,
        reload=True if config.ENV != "production" else False,
        workers=1,
    )

if __name__ == "__main__":
    main()