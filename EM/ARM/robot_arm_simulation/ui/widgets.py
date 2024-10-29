# ui/widgets.py

from matplotlib.widgets import Slider, Button, TextBox

def create_sliders(ax_theta1, ax_theta2, ax_theta3, theta_limits, initial_thetas):
    """
    슬라이더를 생성하고 반환하는 함수.
    """
    slider_theta1 = Slider(ax_theta1, 'Theta1 (°)', theta_limits['theta1_min'], theta_limits['theta1_max'], valinit=initial_thetas['theta1'], valstep=1)
    slider_theta2 = Slider(ax_theta2, 'Theta2 (°)', theta_limits['theta2_min'], theta_limits['theta2_max'], valinit=initial_thetas['theta2'], valstep=1)
    slider_theta3 = Slider(ax_theta3, 'Theta3 (°)', theta_limits['theta3_min'], theta_limits['theta3_max'], valinit=initial_thetas['theta3'], valstep=1)
    
    sliders = {
        'theta1': slider_theta1,
        'theta2': slider_theta2,
        'theta3': slider_theta3
    }
    return sliders

def create_text_boxes(axbox_x, axbox_y, axbox_z):
    """
    텍스트 박스를 생성하고 반환하는 함수.
    """
    text_box_x = TextBox(axbox_x, 'Target X (m)', initial="0.0")
    text_box_y = TextBox(axbox_y, 'Target Y (m)', initial="0.0")
    text_box_z = TextBox(axbox_z, 'Target Z (m)', initial="0.0")
    
    text_boxes = {
        'x': text_box_x,
        'y': text_box_y,
        'z': text_box_z
    }
    return text_boxes

def create_buttons(fig):
    """
    버튼을 생성하고 반환하는 함수.
    """
    # 이동 버튼
    ax_button_move = fig.add_axes([0.75, 0.01, 0.1, 0.04])
    move_button = Button(ax_button_move, 'Move')
    
    # 추가 버튼들
    button_width = 0.1
    button_height = 0.04
    button_spacing = 0.12  # 간격 조절

    # "원위치" 버튼
    ax_button_original = fig.add_axes([0.05, 0.01, button_width, button_height])
    button_original = Button(ax_button_original, 'Original')

    # "불량" 버튼
    ax_button_defect = fig.add_axes([0.05 + button_spacing, 0.01, button_width, button_height])
    button_defect = Button(ax_button_defect, 'Defect')

    # "재사용" 버튼
    ax_button_reuse = fig.add_axes([0.05 + 2*button_spacing, 0.01, button_width, button_height])
    button_reuse = Button(ax_button_reuse, 'Reuse')

    # "컨테이너" 버튼
    ax_button_container = fig.add_axes([0.05 + 3*button_spacing, 0.01, button_width, button_height])
    button_container = Button(ax_button_container, 'Container')

    buttons = {
        'move': move_button,
        'original': button_original,
        'defect': button_defect,
        'reuse': button_reuse,
        'container': button_container
    }
    return buttons
