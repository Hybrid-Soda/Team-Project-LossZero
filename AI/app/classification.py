import cv2
from collections import Counter
from ultralytics import YOLO

colors = {
    "NUT_NORMAL": (255, 0, 0),
    "NUT_REUSABLE": (0, 255, 255),
    "NUT_UNREUSABLE": (0, 0, 255)
}


# 프레임 캡처 함수
def capture_frames(frame_count=5):
    cap = cv2.VideoCapture(0)
    frames = []

    for _ in range(frame_count):
        ret, frame = cap.read()
        if ret:
            frames.append(frame)

    cap.release()

    return frames


# YOLO 모델을 사용하여 객체를 인식하는 함수
def detect_objects(model='yolov11.pt'):
    model = YOLO(model)
    frames = capture_frames()
    results = []

    for frame in frames:
        detections = model(frame, conf=0.6)
        annotated_frame = frame.copy()

        for detection in detections:
            for result in detection.boxes:
                cls = int(result.cls[0])
                label = model.names[cls]
                results.append(label)
                box = result.xyxy[0].cpu().numpy().astype(int)
                cv2.rectangle(annotated_frame, (box[0], box[1]), (box[2], box[3]), colors[label], 2)
                cv2.putText(annotated_frame, label, (box[0], box[1]-10),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.9, colors[label], 2)
        
        cv2.imshow('Detection', annotated_frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    return results


# 최다 등장 객체를 반환하는 함수
def classification():
    classification_map = {
        "NUT_NORMAL": "normal",
        "NUT_REUSABLE": "reusable",
        "NUT_UNREUSABLE": "defective"
    }

    detected_objects = detect_objects('yolov11.pt')

    if detected_objects:
        most_common_obj = Counter(detected_objects).most_common(1)[0][0]
        return classification_map.get(most_common_obj, "unknown")

    return "unknown"
