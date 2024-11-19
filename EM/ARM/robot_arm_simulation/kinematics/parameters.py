# kinematics/parameters.py

import numpy as np

# DH 파라미터 정의: (alpha, a, d, theta_label)
DH_PARAMS = [
    (np.pi/2, 0, 0.96, 'theta1'),
    (0, 0.65, 0, 'theta2'),
    (0, 1.4, 0, 'theta3'),
    (0, 0.2, 0, '-pi/2')  # θ4는 고정값
]

# 조인트 각도 한계 (도 단위)
JOINT_LIMITS = {
    'theta1_min': 0,
    'theta1_max': 180,
    'theta2_min': 15,
    'theta2_max': 165,
    'theta3_min': -90,
    'theta3_max': 90
}
