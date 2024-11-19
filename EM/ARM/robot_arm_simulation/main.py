# main.py

import numpy as np
import matplotlib
matplotlib.use('Qt5Agg')  # 또는 다른 GUI 백엔드로 변경
import matplotlib.pyplot as plt
from matplotlib.widgets import Slider, Button, TextBox

# 사용자 정의 모듈 임포트
from kinematics.parameters import DH_PARAMS, JOINT_LIMITS
from kinematics.forward import compute_joint_positions
from kinematics.inverse import inverse_kinematics_planar
from visualization.setup import setup_font, initialize_plot
from visualization.plotting import update_plot, animate_movement
from ui.widgets import create_sliders, create_text_boxes, create_buttons
from ui.callbacks import (
    move_to_position,
    move_robot_to,
    move_to_original,
    move_to_defect,
    move_to_reuse,
    move_to_container
)

def main():
    # 한글 폰트 설정
    setup_font()

    # 초기 각도 설정 (도 단위)
    initial_thetas = {
        'theta1': 90.0,
        'theta2': 70.0,
        'theta3': -90.0
    }

    # Plot 초기화
    fig, ax, line, ee_text = initialize_plot()
    update_plot(ax, line, ee_text, compute_joint_positions, initial_thetas['theta1'], initial_thetas['theta2'], initial_thetas['theta3'])

    # 슬라이더 위치 조정
    ax_theta1 = plt.axes([0.25, 0.25, 0.65, 0.03])
    ax_theta2 = plt.axes([0.25, 0.20, 0.65, 0.03])
    ax_theta3 = plt.axes([0.25, 0.15, 0.65, 0.03])
    sliders = create_sliders(ax_theta1, ax_theta2, ax_theta3, JOINT_LIMITS, initial_thetas)

    # 슬라이더 업데이트 함수
    def slider_update(val):
        theta1_deg = sliders['theta1'].val
        theta2_deg = sliders['theta2'].val
        theta3_deg = sliders['theta3'].val
        update_plot(ax, line, ee_text, compute_joint_positions, theta1_deg, theta2_deg, theta3_deg)

    sliders['theta1'].on_changed(slider_update)
    sliders['theta2'].on_changed(slider_update)
    sliders['theta3'].on_changed(slider_update)

    # 목표 위치 입력을 위한 텍스트 박스 추가
    axbox_x = plt.axes([0.25, 0.05, 0.2, 0.04])
    axbox_y = plt.axes([0.5, 0.05, 0.2, 0.04])
    axbox_z = plt.axes([0.75, 0.05, 0.2, 0.04])
    text_boxes = create_text_boxes(axbox_x, axbox_y, axbox_z)

    # 버튼 추가
    buttons = create_buttons(fig)

    # 콜백 함수 연결
    buttons['move'].on_clicked(lambda event: move_to_position(
        event,
        text_boxes,
        sliders,
        compute_joint_positions,
        lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
        inverse_kinematics_planar,
        lambda new_t1, new_t2, new_t3: animate_movement(
            sliders,
            compute_joint_positions,
            lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
            new_t1,
            new_t2,
            new_t3
        ),
        JOINT_LIMITS
    ))

    buttons['original'].on_clicked(lambda event: move_to_original(
        event,
        lambda pos: move_robot_to(
            pos,
            inverse_kinematics_planar,
            sliders,
            compute_joint_positions,
            lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
            lambda new_t1, new_t2, new_t3: animate_movement(
                sliders,
                compute_joint_positions,
                lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
                new_t1,
                new_t2,
                new_t3
            ),
            JOINT_LIMITS
        )
    ))

    buttons['defect'].on_clicked(lambda event: move_to_defect(
        event,
        lambda pos: move_robot_to(
            pos,
            inverse_kinematics_planar,
            sliders,
            compute_joint_positions,
            lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
            lambda new_t1, new_t2, new_t3: animate_movement(
                sliders,
                compute_joint_positions,
                lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
                new_t1,
                new_t2,
                new_t3
            ),
            JOINT_LIMITS
        )
    ))

    buttons['reuse'].on_clicked(lambda event: move_to_reuse(
        event,
        lambda pos: move_robot_to(
            pos,
            inverse_kinematics_planar,
            sliders,
            compute_joint_positions,
            lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
            lambda new_t1, new_t2, new_t3: animate_movement(
                sliders,
                compute_joint_positions,
                lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
                new_t1,
                new_t2,
                new_t3
            ),
            JOINT_LIMITS
        )
    ))

    buttons['container'].on_clicked(lambda event: move_to_container(
        event,
        lambda pos: move_robot_to(
            pos,
            inverse_kinematics_planar,
            sliders,
            compute_joint_positions,
            lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
            lambda new_t1, new_t2, new_t3: animate_movement(
                sliders,
                compute_joint_positions,
                lambda t1, t2, t3: update_plot(ax, line, ee_text, compute_joint_positions, t1, t2, t3),
                new_t1,
                new_t2,
                new_t3
            ),
            JOINT_LIMITS
        )
    ))

    plt.show()

if __name__ == "__main__":
    main()
