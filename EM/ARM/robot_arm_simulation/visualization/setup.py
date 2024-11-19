# visualization/setup.py

import matplotlib.pyplot as plt
from matplotlib import rc

def setup_font():
    try:
        # 마이너스 기호 설정을 False로 변경하여 ASCII 하이픈 사용
        plt.rcParams['axes.unicode_minus'] = False
    except Exception as e:
        print(f"폰트 설정 오류: {e}")
        # 폰트 설정 실패 시 기본 설정 유지

def initialize_plot():
    """
    플롯을 초기화하고 기본 설정을 하는 함수.
    """
    fig = plt.figure(figsize=(10, 8))
    ax = fig.add_subplot(111, projection='3d')
    plt.subplots_adjust(left=0.25, bottom=0.35)
    ax.set_xlabel('X (m)')
    ax.set_ylabel('Y (m)')
    ax.set_zlabel('Z (m)')
    ax.set_title('3D Robot Arm Simulation')
    ax.set_xlim(-3, 3)
    ax.set_ylim(-3, 3)
    ax.set_zlim(0, 3)
    ax.view_init(elev=30, azim=45)
    ax.grid(True)

    line, = ax.plot([], [], [], 'o-', linewidth=4, markersize=6, markerfacecolor='red')
    ee_text = ax.text2D(0.05, 0.95, "", transform=ax.transAxes)

    return fig, ax, line, ee_text
