from pi5neo import Pi5Neo
import time

def rainbow_cycle(neo, delay=0.1):
    colors = [
        (255, 0, 0),  # Red
        (255, 127, 0),  # Orange
        (255, 255, 0),  # Yellow
        (0, 255, 0),  # Green
        (0, 0, 255),  # Blue
        (75, 0, 130),  # Indigo
        (148, 0, 211)  # Violet
    ]
    for color in colors:
        neo.fill_strip(*color)
        neo.update_strip()
        time.sleep(delay)

# 메인 실행 블록
if __name__ == "__main__":
    neo = Pi5Neo('/dev/spidev0.0', 8, 800)  # 네오픽셀 객체 생성
    
    try:
        rainbow_cycle(neo)  # 무지개 색상 실행
    except KeyboardInterrupt:
        print("\nCtrl+C detected! Cleaning up...")
    finally:
        # 핀 초기화 (LED 끄기)
        neo.fill_strip(0, 0, 0)  # 모든 LED를 검은색으로 설정 (OFF 상태)
        neo.update_strip()
        print("Pins reset. Exiting program.")
