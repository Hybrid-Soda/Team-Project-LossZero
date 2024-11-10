import cv2
from collections import Counter
from ultralytics import YOLO


# 프레임 캡처 함수 (10장)
def capture_frames(frame_count=10):
    cap = cv2.VideoCapture(0)
    frames = []

    for _ in range(frame_count):
        ret, frame = cap.read()
        if ret:
            frames.append(frame)
    cap.release()

    return frames


# YOLO 모델을 사용하여 객체를 인식하는 함수
def detect_objects():
    model = YOLO('best.pt')
    frames = capture_frames()
    results = []

    for frame in frames:
        detections = model(frame)

        for detection in detections:
            for result in detection.boxes:
                cls = int(result.cls[0])
                results.append(model.names[cls])

    return results


# 최다 등장 객체를 반환하는 함수
def classification():
    classification_map = {
        "NUT_NORMAL": "normal",
        "NUT_REUSABLE": "reusable",
        "NUT_UNREUSABLE": "defective"
    }

    detected_objects = detect_objects()

    if detected_objects:
        most_common_obj = Counter(detected_objects).most_common(1)[0][0]
        return classification_map.get(most_common_obj, "unknown")

    return classification_map["NUT_NORMAL"]

print(classification())