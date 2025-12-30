/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.vr_software.vr_software;

import com.vr_software.vr_software.frame.PedidoFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Usuario
 */
public class Vr_software {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PedidoFrame().setVisible(true);
        });
    }
}
