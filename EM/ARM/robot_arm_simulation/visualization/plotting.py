# visualization/plotting.py

import matplotlib.pyplot as plt

def update_plot(ax, line, ee_text, compute_joint_positions, theta1_deg, theta2_deg, theta3_deg):
    """
    플롯을 업데이트하는 함수.
    """
    positions = compute_joint_positions(theta1_deg, theta2_deg, theta3_deg)
    x = [pos[0] for pos in positions]
    y = [pos[1] for pos in positions]
    z = [pos[2] for pos in positions]
    line.set_data(x, y)
    line.set_3d_properties(z)
    ee_text.set_text(f"End Effector Position: X={x[-1]:.2f}m, Y={y[-1]:.2f}m, Z={z[-1]:.2f}m")
    plt.draw()

def animate_movement(sliders, compute_joint_positions, update_plot_func, theta1_deg, theta2_deg, theta3_deg):
    """
    로봇 팔의 움직임을 애니메이션으로 보여주는 함수.
    """
    import matplotlib.pyplot as plt
    current_theta1 = sliders['theta1'].val
    current_theta2 = sliders['theta2'].val
    current_theta3 = sliders['theta3'].val
    steps = 100
    for i in range(1, steps + 1):
        interpolated_theta1 = current_theta1 + (theta1_deg - current_theta1) * (i / steps)
        interpolated_theta2 = current_theta2 + (theta2_deg - current_theta2) * (i / steps)
        interpolated_theta3 = current_theta3 + (theta3_deg - current_theta3) * (i / steps)
        sliders['theta1'].set_val(interpolated_theta1)
        sliders['theta2'].set_val(interpolated_theta2)
        sliders['theta3'].set_val(interpolated_theta3)
        update_plot_func(interpolated_theta1, interpolated_theta2, interpolated_theta3)
        plt.pause(0.001)  # 실시간으로 움직이는 효과
