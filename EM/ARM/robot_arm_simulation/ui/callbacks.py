# ui/callbacks.py

import numpy as np
import time

def move_to_position(event, text_boxes, sliders, compute_joint_positions, update_plot, inverse_kinematics, animate_movement, theta_limits):
    try:
        # 입력된 목표 위치 값 가져오기
        desired_x = float(text_boxes['x'].text)
        desired_y = float(text_boxes['y'].text)
        desired_z = float(text_boxes['z'].text)

        # theta1 계산 (arctan2으로 방향 결정)
        theta1_rad = np.arctan2(desired_y, desired_x)
        theta1_deg = np.rad2deg(theta1_rad)

        # theta1을 [0, 180] 범위로 조정
        theta1_deg = adjust_theta1(theta1_deg, theta_limits['theta1_min'], theta_limits['theta1_max'])

        # r 계산 (XY 평면에서의 거리)
        r = np.sqrt(desired_x**2 + desired_y**2)
        z = desired_z

        # 현재 theta2와 theta3 각도 가져오기
        current_theta2_deg = sliders['theta2'].val
        current_theta3_deg = sliders['theta3'].val

        # 평면 내에서 theta2와 theta3 계산
        ik_history = inverse_kinematics(r, z, current_theta2_deg, current_theta3_deg)
        if ik_history.size == 0:
            print("목표 위치로의 역기구학 해결 실패!")
            return
        new_theta2_deg, new_theta3_deg = ik_history[-1]

        # 슬라이더와 로봇 팔 애니메이션 업데이트
        animate_movement(theta1_deg, new_theta2_deg, new_theta3_deg)
        print("목표 위치로 이동 완료!")

    except ValueError:
        print("올바른 숫자를 입력하세요.")
    except Exception as e:
        print(f"이동 중 오류 발생: {e}")

def move_robot_to(desired_pos, inverse_kinematics, sliders, compute_joint_positions, update_plot, animate_movement, theta_limits):
    try:
        desired_x, desired_y, desired_z = desired_pos
        # theta1 계산 (arctan2으로 방향 결정)
        theta1_rad = np.arctan2(desired_y, desired_x)
        theta1_deg = np.rad2deg(theta1_rad)

        # theta1을 [0, 180] 범위로 조정
        theta1_deg = adjust_theta1(theta1_deg, theta_limits['theta1_min'], theta_limits['theta1_max'])

        # r 계산 (XY 평면에서의 거리)
        r = np.sqrt(desired_x**2 + desired_y**2)
        z = desired_z

        # 현재 theta2와 theta3 각도 가져오기
        current_theta2_deg = sliders['theta2'].val
        current_theta3_deg = sliders['theta3'].val

        # 평면 내에서 theta2와 theta3 계산
        ik_history = inverse_kinematics(r, z, current_theta2_deg, current_theta3_deg)
        if ik_history.size == 0:
            print("목표 위치로의 역기구학 해결 실패!")
            return
        new_theta2_deg, new_theta3_deg = ik_history[-1]

        # 슬라이더와 로봇 팔 애니메이션 업데이트
        animate_movement(theta1_deg, new_theta2_deg, new_theta3_deg)
        print(f"목표 위치 {desired_pos}로 이동 완료!")

    except Exception as e:
        print(f"이동 중 오류 발생: {e}")

def move_to_original(event, move_robot_func):
    """
    원위치로 이동하는 시퀀스 정의.
    """
    desired_pos = np.array([0.0, 1.5, 1.0])
    move_robot_func(desired_pos)

def move_to_defect(event, move_robot_func):
    """
    불량 이동 시퀀스: (-1, 1.3, 1) -> (-0.95, 1.25, 0.5)
    """
    positions = [
        np.array([-1.0, 1.3, 1]),
        np.array([-0.95, 1.25, 0.5])
    ]
    for pos in positions:
        move_robot_func(pos)
        time.sleep(1)  # 1초 대기

def move_to_reuse(event, move_robot_func):
    """
    재사용 이동 시퀀스: (-1.3, 0.7, 1) -> (-1.2, 0.7, 0.5)
    """
    positions = [
        np.array([-1.3, 0.7, 1]),
        np.array([-1.2, 0.7, 0.5])
    ]
    for pos in positions:
        move_robot_func(pos)
        time.sleep(1)  # 1초 대기

def move_to_container(event, move_robot_func):
    """
    컨테이너 이동 시퀀스: (1.6, 1, 1) -> (1.45, 1, 0.5)
    """
    positions = [
        np.array([1.6, 1.0, 1.0]),
        np.array([1.45, 1.0, 0.5])
    ]
    for pos in positions:
        move_robot_func(pos)
        time.sleep(1)  # 1초 대기

def adjust_theta1(theta1_deg, theta1_min, theta1_max):
    """
    theta1을 [theta1_min, theta1_max] 범위로 조정하는 함수.
    """
    if theta1_deg < theta1_min:
        theta1_deg += 180
    elif theta1_deg > theta1_max:
        theta1_deg -= 180

    # theta1 한계 내로 클램핑
    theta1_deg = np.clip(theta1_deg, theta1_min, theta1_max)
    return theta1_deg
