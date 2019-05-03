package random.gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

class About extends javax.swing.JFrame {

    About() throws IOException {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        BufferedImage icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("loupe.png"));
        setIconImage(icon);
        setTitle("About");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        rbTxtCheck = new javax.swing.JRadioButton();
        pMainAbout = new javax.swing.JPanel();
        pTxtStego = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        imgAb = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        pTxtCheck = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

        setAlwaysOnTop(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("shterneregen");

        jButton1.setText("Exit");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        rbTxtCheck.setText("Открыть описание инструментов проверки");
        rbTxtCheck.addActionListener(this::rbTxtCheckActionPerformed);

        pMainAbout.setLayout(new java.awt.CardLayout());

        pTxtStego.setMaximumSize(new java.awt.Dimension(825, 335));
        pTxtStego.setMinimumSize(new java.awt.Dimension(825, 335));
        pTxtStego.setName(""); // NOI18N
        pTxtStego.setPreferredSize(new java.awt.Dimension(825, 335));

        jLabel2.setText("Для выбора картинки нажмите кнопку \"Загрузить из файла\". Чтобы");
        jLabel3.setText("записать текст в картинку введите в текстовом поле нужное сообщение ");
        jLabel4.setText("и нажмите кнопку \"Вставить сообщение\".");
        jLabel5.setText("файл\".");
        jLabel6.setText("Чтобы извлечь сообщение из картинки, выберите картинку и нажмите ");
        jLabel7.setText("кнопку \"Извлечь сообщение\", если сообщение присутствует, то оно ");
        jLabel13.setText("Начало места записи (номер пикселя) сообщения в изображения можно");
        jLabel14.setText("ввести в текстовом поле рядом с кнопкой \"Ввести № пикселя\" и затем ");
        jLabel22.setText("нажать на эту кнопку.");
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Описание работы с инструментами записи и извлечений сообщения");
        imgAb.setForeground(new java.awt.Color(240, 240, 240));
        jLabel24.setText("появится в текстовом поле.");
        jLabel26.setText("Для сохранения получившегося изображения нажмите кнопку \"Сохранить ");

        javax.swing.GroupLayout pTxtStegoLayout = new javax.swing.GroupLayout(pTxtStego);
        pTxtStego.setLayout(pTxtStegoLayout);
        pTxtStegoLayout.setHorizontalGroup(
                pTxtStegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pTxtStegoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pTxtStegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pTxtStegoLayout.createSequentialGroup()
                                                .addGroup(pTxtStegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel23)
                                                        .addComponent(jLabel2))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(pTxtStegoLayout.createSequentialGroup()
                                                .addGroup(pTxtStegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel14)
                                                        .addComponent(jLabel22)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel26)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel24)
                                                        .addComponent(jLabel13))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                                .addComponent(imgAb, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        pTxtStegoLayout.setVerticalGroup(
                pTxtStegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pTxtStegoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pTxtStegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pTxtStegoLayout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel26)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel24)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel14)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel22))
                                        .addComponent(imgAb, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(28, Short.MAX_VALUE))
        );

        pMainAbout.add(pTxtStego, "card3");

        pTxtCheck.setMaximumSize(new java.awt.Dimension(825, 335));
        pTxtCheck.setMinimumSize(new java.awt.Dimension(825, 335));

        jLabel8.setText("В методе скользящего среднего берутся последние биты изображения в определенном интервале, суммируются, и эта сумма делится на интервал проверки. ");
        jLabel9.setText("На основании этих средних сначений строится график. Для задания начального и последнего пикселя, в границах которых будет происходить проверка, ");
        jLabel10.setText("в текстовые поля рядом с кнопкой \"Ввести границы\", следует ввести номера пикселей и нажть эту кнопку. Чтобы выбрать интервал, через который будет");
        jLabel11.setText("происходить суммирование, необходимо в текстовое поле рядом с кнопкой \"Ввести интервал\", ввести интервал, затем нажать на эту кнопку. Построение ");
        jLabel12.setText(" графика осуществляется после нажатия кнопки \"График\".");
        jLabel15.setText("необходимо нажать на кнопку \"Изображение на основе последних бит\". Полученной изображение можно сохранить с помощь кнопки \"Сохранить файл\".");
        jLabel16.setText("В визуальном методе определения строится проверочное изображение на основе последних бит исходного изображения. Чтобы использовать этот метод,");
        jLabel17.setText("В методе вычисления энтропии суммируются все едиицы, взятые из пикселей на заданном интервале, и затем делятся на этот интервал, таким образом");
        jLabel18.setText("определяется вероятность появления \"1\" в интервале, затем вычисляется вероятность появления \"0\". На основании этих вероятноестей вычисляется");
        jLabel19.setText("энтропия. На основании энтрпоий интревало строится график. Для задания начального и последнего пикселя, в границах которых будет происходить");
        jLabel20.setText("проверка, в текстовые поля рядом с кнопкой \"Ввести границы\", следует ввести номера пикселей и нажть эту кнопку. Чтобы выбрать интервал, через");
        jLabel21.setText("который будет происходить суммирование, необходимо в текстовое поле рядом с кнопкой \"Ввести интервал\",  ввести интервал, затем нажать на эту");
        jLabel25.setText(" кнопку. Построение графика осуществляется после нажатия кнопки \"График\".");
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Описание инструментов методов проверки");

        javax.swing.GroupLayout pTxtCheckLayout = new javax.swing.GroupLayout(pTxtCheck);
        pTxtCheck.setLayout(pTxtCheckLayout);
        pTxtCheckLayout.setHorizontalGroup(
                pTxtCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pTxtCheckLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pTxtCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel20)
                                        .addComponent(jLabel25)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel27)
                                        .addComponent(jLabel21)))
        );
        pTxtCheckLayout.setVerticalGroup(
                pTxtCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTxtCheckLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25)
                                .addContainerGap())
        );

        pMainAbout.add(pTxtCheck, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(rbTxtCheck)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pMainAbout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pMainAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(rbTxtCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jButton1))
                                .addGap(15, 15, 15))
        );

        pack();
    }
    // </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void rbTxtCheckActionPerformed(java.awt.event.ActionEvent evt) {
        if (rbTxtCheck.isSelected()) {
            pMainAbout.removeAll();
            pMainAbout.add(pTxtCheck);
            pMainAbout.repaint();
            pMainAbout.revalidate();
        } else {
            pMainAbout.removeAll();
            pMainAbout.add(pTxtStego);
            pMainAbout.repaint();
            pMainAbout.revalidate();
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel imgAb;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pMainAbout;
    private javax.swing.JPanel pTxtCheck;
    private javax.swing.JPanel pTxtStego;
    private javax.swing.JRadioButton rbTxtCheck;
    // End of variables declaration
}
