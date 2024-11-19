# kinematics/forward.py

import numpy as np
from .transforms import DHTransform
from .parameters import DH_PARAMS

def compute_joint_positions(theta1_deg, theta2_deg, theta3_deg):
    """
    주어진 각도에 따라 조인트 위치를 계산하는 함수.
    """
    theta1 = np.deg2rad(theta1_deg)
    theta2 = np.deg2rad(theta2_deg)
    theta3 = np.deg2rad(theta3_deg)

    T = np.identity(4)
    positions = [T[:3, 3].copy()]
    thetas = [theta1, theta2, theta3, -np.pi/2]
    for i, (alpha, a, d, theta_label) in enumerate(DH_PARAMS):
        if theta_label.startswith('theta'):
            theta = thetas[i]
        else:
            theta = thetas[i]
        T_link = DHTransform(alpha, a, d, theta)
        T = T @ T_link
        positions.append(T[:3, 3].copy())
    return positions
