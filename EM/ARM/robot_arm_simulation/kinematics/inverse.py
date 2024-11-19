# kinematics/inverse.py

import numpy as np
from .transforms import DHTransform
from .parameters import DH_PARAMS, JOINT_LIMITS

def inverse_kinematics_planar(r, z, current_theta2_deg, current_theta3_deg, max_iterations=1000, tolerance=1e-3, alpha=0.1):
    """
    평면 내에서 theta2와 theta3을 계산하는 역기구학 함수.
    """
    theta2 = np.deg2rad(current_theta2_deg)
    theta3 = np.deg2rad(current_theta3_deg)
    angles = np.array([theta2, theta3])
    history = [angles.copy()]

    for _ in range(max_iterations):
        # 현재 위치 계산
        T = np.identity(4)
        # theta1는 이미 고정되어 있다고 가정 (0 라디안)
        T_link1 = DHTransform(DH_PARAMS[0][0], DH_PARAMS[0][1], DH_PARAMS[0][2], 0)  # theta1 고정
        T = T @ T_link1
        # theta2
        T_link2 = DHTransform(DH_PARAMS[1][0], DH_PARAMS[1][1], DH_PARAMS[1][2], angles[0])
        T = T @ T_link2
        # theta3
        T_link3 = DHTransform(DH_PARAMS[2][0], DH_PARAMS[2][1], DH_PARAMS[2][2], angles[1])
        T = T @ T_link3
        current_pos = T[:3, 3]

        # 목표 위치
        desired_pos = np.array([r, 0, z])

        # 오차 계산 (r과 z만 고려)
        current_r = np.sqrt(current_pos[0]**2 + current_pos[1]**2)
        error = np.array([r - current_r, z - current_pos[2]])
        error_norm = np.linalg.norm(error)
        if error_norm < tolerance:
            break

        # Jacobian 계산: Partial derivatives of r and z with respect to theta2 and theta3
        J = np.zeros((2, 2))
        delta = 1e-6
        for i in range(2):
            angles_delta = angles.copy()
            angles_delta[i] += delta
            # Recompute position with perturbed angle
            T_temp = np.identity(4)
            T_temp = T_temp @ DHTransform(DH_PARAMS[0][0], DH_PARAMS[0][1], DH_PARAMS[0][2], 0)
            T_temp = T_temp @ DHTransform(DH_PARAMS[1][0], DH_PARAMS[1][1], DH_PARAMS[1][2], angles_delta[0])
            T_temp = T_temp @ DHTransform(DH_PARAMS[2][0], DH_PARAMS[2][1], DH_PARAMS[2][2], angles_delta[1])
            pos_delta = T_temp[:3, 3]
            r_delta = np.sqrt(pos_delta[0]**2 + pos_delta[1]**2)
            z_delta = pos_delta[2]
            J[:, i] = [(r_delta - current_r) / delta, (z_delta - current_pos[2]) / delta]

        # Jacobian Pseudoinverse 사용
        J_pseudo_inv = np.linalg.pinv(J)
        delta_angles = alpha * (J_pseudo_inv @ error)
        angles += delta_angles

        # 관절 한계 적용
        angles[0] = np.clip(angles[0], np.deg2rad(JOINT_LIMITS['theta2_min']), np.deg2rad(JOINT_LIMITS['theta2_max']))
        angles[1] = np.clip(angles[1], np.deg2rad(JOINT_LIMITS['theta3_min']), np.deg2rad(JOINT_LIMITS['theta3_max']))

        history.append(angles.copy())

    return np.rad2deg(np.array(history))
