package random.gui;

import random.Util;
import random.imageinspection.ImageInspection;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import random.stego.Steganography;

public class MainFrame extends javax.swing.JFrame {

    private BufferedImage openImg;          // Выбранное с помощью диалога изображение
    private BufferedImage imgNew;
    private BufferedImage forShow;          // Изображение для вывода на экран
    private int[] pix, oldPix;
    private int nImageWidth, nImageHeight;   // Ширина и высота выбранного изображения
    private int intrvAvg, intrvEntropy;
    private int boundEntropy1, boundEntropy2, boundAvg1, boundAvg2;
    private int numOfPix;               // № пикселя, с которого происходит запись в изображение
    private boolean fStart;

    public MainFrame() throws IOException {
        intrvAvg = 0;
        intrvEntropy = 0;
        boundEntropy1 = 0;
        boundEntropy2 = 0;
        boundAvg1 = 0;
        boundAvg2 = 0;
        numOfPix = 0;
        fStart = false;

        Image icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("loupe.png"));
        setIconImage(icon);
        setTitle("Steganography");

        initComponents();
        setResizable(false);        // Запрещает изменение размера окна программы      
        setLocationRelativeTo(null);// Окно появляется по центру экрана
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        fileSaver = new javax.swing.JFileChooser();
        fileChooser1 = new javax.swing.JFileChooser();
        pFirstImg = new javax.swing.JPanel();
        lbFirstImg = new javax.swing.JLabel();
        pSecondImg = new javax.swing.JPanel();
        lbSecondImg = new javax.swing.JLabel();
        btExt = new javax.swing.JButton();
        lTrace = new javax.swing.JLabel();
        pStegoChecks = new javax.swing.JPanel();
        pStego = new javax.swing.JPanel();
        btLoad = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpInsText = new javax.swing.JTextPane();
        btInsert = new javax.swing.JButton();
        btExtract = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        tfNumOfPix = new javax.swing.JTextField();
        btInsNumPix = new javax.swing.JButton();
        pChecks = new javax.swing.JPanel();
        pAvg = new javax.swing.JPanel();
        tfIntervalAvg = new javax.swing.JTextField();
        btInsIntervAvg = new javax.swing.JButton();
        btGraphAvg = new javax.swing.JButton();
        tfBoundAvg1 = new javax.swing.JTextField();
        tfBoundAvg2 = new javax.swing.JTextField();
        btInsBoundAvg = new javax.swing.JButton();
        pVisMethod = new javax.swing.JPanel();
        btLastBit = new javax.swing.JButton();
        pEntropy = new javax.swing.JPanel();
        tfBoundEnt1 = new javax.swing.JTextField();
        tfBoundEnt2 = new javax.swing.JTextField();
        btInsBoundEnt = new javax.swing.JButton();
        btGraphEnt = new javax.swing.JButton();
        btInsIntervEnt = new javax.swing.JButton();
        tfIntervalEntropy = new javax.swing.JTextField();
        btLoadCheck = new javax.swing.JButton();
        btSaveCheck = new javax.swing.JButton();
        rbOpenTools = new javax.swing.JRadioButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        open = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        toBMP = new javax.swing.JMenuItem();
        closeMenu = new javax.swing.JMenuItem();
        infMenu = new javax.swing.JMenu();
        aboutBt = new javax.swing.JMenuItem();

        fileSaver.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileSaver.setApproveButtonText("Save");
        fileSaver.setControlButtonsAreShown(false);

        fileChooser1.setCurrentDirectory(null);
        fileChooser1.setFileFilter(new BmpFilter());
        fileChooser1.getAccessibleContext().setAccessibleName("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pFirstImg.setBorder(javax.swing.BorderFactory.createTitledBorder("Original image"));
        pFirstImg.setMaximumSize(new java.awt.Dimension(507, 300));
        pFirstImg.setMinimumSize(new java.awt.Dimension(507, 300));
        pFirstImg.setName(""); // NOI18N

        lbFirstImg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbFirstImg.setInheritsPopupMenu(false);
        lbFirstImg.setMaximumSize(new java.awt.Dimension(475, 255));
        lbFirstImg.setMinimumSize(new java.awt.Dimension(475, 255));
        lbFirstImg.setName(""); // NOI18N
        lbFirstImg.setPreferredSize(new java.awt.Dimension(475, 255));

        GroupLayout pFirstImgLayout = new GroupLayout(pFirstImg);
        pFirstImg.setLayout(pFirstImgLayout);
        pFirstImgLayout.setHorizontalGroup(
                pFirstImgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pFirstImgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbFirstImg, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        pFirstImgLayout.setVerticalGroup(
                pFirstImgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pFirstImgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbFirstImg, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pSecondImg.setBorder(javax.swing.BorderFactory.createTitledBorder("Image with hidden message"));
        pSecondImg.setMaximumSize(new java.awt.Dimension(507, 300));
        pSecondImg.setMinimumSize(new java.awt.Dimension(507, 300));
        pSecondImg.setName(""); // NOI18N

        lbSecondImg.setMaximumSize(new java.awt.Dimension(475, 255));
        lbSecondImg.setMinimumSize(new java.awt.Dimension(475, 255));
        lbSecondImg.setPreferredSize(new java.awt.Dimension(475, 255));

        GroupLayout pSecondImgLayout = new GroupLayout(pSecondImg);
        pSecondImg.setLayout(pSecondImgLayout);
        pSecondImgLayout.setHorizontalGroup(
                pSecondImgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pSecondImgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbSecondImg, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pSecondImgLayout.setVerticalGroup(
                pSecondImgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pSecondImgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbSecondImg, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        btExt.setText("Exit");
        btExt.addActionListener(this::btExtActionPerformed);

        lTrace.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lTrace.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pStegoChecks.setLayout(new java.awt.CardLayout());

        pStego.setBorder(javax.swing.BorderFactory.createTitledBorder("Record and extract tools"));

        btLoad.setText("Load image");
        btLoad.addActionListener(evt -> preImageLoad());

        tpInsText.setMaximumSize(new java.awt.Dimension(346, 335));
        tpInsText.setMinimumSize(new java.awt.Dimension(346, 335));
        tpInsText.setPreferredSize(new java.awt.Dimension(346, 335));
        jScrollPane1.setViewportView(tpInsText);

        btInsert.setText("Insert message");
        btInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btInsert.addActionListener(evt -> btInsertActionPerformed());

        btExtract.setText("Extract message");
        btExtract.addActionListener(this::btExtractActionPerformed);

        btSave.setText("Save file");
        btSave.addActionListener(this::btSaveActionPerformed);

        btInsNumPix.setText("Enter pixel number");
        btInsNumPix.addActionListener(this::btInsNumPixActionPerformed);

        GroupLayout pStegoLayout = new GroupLayout(pStego);
        pStego.setLayout(pStegoLayout);
        pStegoLayout.setHorizontalGroup(
                pStegoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pStegoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pStegoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(pStegoLayout.createSequentialGroup()
                                                .addGroup(pStegoLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btInsert, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btLoad, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(pStegoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(btExtract, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(pStegoLayout.createSequentialGroup()
                                                .addComponent(tfNumOfPix, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btInsNumPix, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        pStegoLayout.setVerticalGroup(
                pStegoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, pStegoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pStegoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btLoad)
                                        .addComponent(btSave))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pStegoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btInsert)
                                        .addComponent(btExtract))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pStegoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(btInsNumPix)
                                        .addComponent(tfNumOfPix, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(98, 98, 98))
        );

        pStegoChecks.add(pStego, "card2");

        pChecks.setBorder(javax.swing.BorderFactory.createTitledBorder("Inspection tools"));
        pChecks.setToolTipText("");

        pAvg.setBorder(javax.swing.BorderFactory.createTitledBorder("Moving average method"));

        btInsIntervAvg.setText("Enter interval");
        btInsIntervAvg.addActionListener(this::btInsIntervAvgActionPerformed);

        btGraphAvg.setText("Chart");
        btGraphAvg.addActionListener(this::btGraphAvgActionPerformed);

        btInsBoundAvg.setText("Enter borders");
        btInsBoundAvg.addActionListener(this::btInsBoundAvgActionPerformed);

        GroupLayout pAvgLayout = new GroupLayout(pAvg);
        pAvg.setLayout(pAvgLayout);
        pAvgLayout.setHorizontalGroup(
                pAvgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pAvgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pAvgLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tfBoundAvg1, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addComponent(tfIntervalAvg))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pAvgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pAvgLayout.createSequentialGroup()
                                                .addComponent(tfBoundAvg2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btInsBoundAvg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(pAvgLayout.createSequentialGroup()
                                                .addComponent(btInsIntervAvg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btGraphAvg, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        pAvgLayout.setVerticalGroup(
                pAvgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pAvgLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pAvgLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfBoundAvg1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfBoundAvg2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btInsBoundAvg))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pAvgLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfIntervalAvg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btInsIntervAvg)
                                        .addComponent(btGraphAvg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pVisMethod.setBorder(javax.swing.BorderFactory.createTitledBorder("Visual definition method"));

        btLastBit.setText("Last bit image");
        btLastBit.addActionListener(this::btLastBitActionPerformed);

        GroupLayout pVisMethodLayout = new GroupLayout(pVisMethod);
        pVisMethod.setLayout(pVisMethodLayout);
        pVisMethodLayout.setHorizontalGroup(
                pVisMethodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pVisMethodLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btLastBit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pVisMethodLayout.setVerticalGroup(
                pVisMethodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, pVisMethodLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btLastBit)
                                .addContainerGap())
        );

        pEntropy.setBorder(javax.swing.BorderFactory.createTitledBorder("Entropy calculation method"));

        btInsBoundEnt.setText("Enter borders");
        btInsBoundEnt.addActionListener(this::btInsBoundEntActionPerformed);

        btGraphEnt.setText("Chart");
        btGraphEnt.addActionListener(this::btGraphEntActionPerformed);

        btInsIntervEnt.setText("Enter interval");
        btInsIntervEnt.addActionListener(this::btInsIntervEntActionPerformed);

        GroupLayout pEntropyLayout = new GroupLayout(pEntropy);
        pEntropy.setLayout(pEntropyLayout);
        pEntropyLayout.setHorizontalGroup(
                pEntropyLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pEntropyLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(pEntropyLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tfBoundEnt1, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addComponent(tfIntervalEntropy))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pEntropyLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pEntropyLayout.createSequentialGroup()
                                                .addComponent(tfBoundEnt2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btInsBoundEnt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(pEntropyLayout.createSequentialGroup()
                                                .addComponent(btInsIntervEnt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btGraphEnt, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        pEntropyLayout.setVerticalGroup(
                pEntropyLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pEntropyLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pEntropyLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfBoundEnt1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfBoundEnt2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btInsBoundEnt))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pEntropyLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfIntervalEntropy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btInsIntervEnt)
                                        .addComponent(btGraphEnt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        btLoadCheck.setText("Load from file");
        btLoadCheck.addActionListener(evt -> preImageLoad());

        btSaveCheck.setText("Save file");
        btSaveCheck.addActionListener(this::btSaveCheckActionPerformed);

        GroupLayout pChecksLayout = new GroupLayout(pChecks);
        pChecks.setLayout(pChecksLayout);
        pChecksLayout.setHorizontalGroup(
                pChecksLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pChecksLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pChecksLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pChecksLayout.createSequentialGroup()
                                                .addComponent(btLoadCheck, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btSaveCheck, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                                        .addComponent(pEntropy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pAvg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pVisMethod, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pChecksLayout.setVerticalGroup(
                pChecksLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pChecksLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pAvg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(pVisMethod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(pEntropy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pChecksLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btLoadCheck)
                                        .addComponent(btSaveCheck))
                                .addGap(122, 122, 122))
        );

        pStegoChecks.add(pChecks, "card3");

        rbOpenTools.setText("Open validation tools");
        rbOpenTools.addActionListener(this::rbOpenToolsActionPerformed);

        fileMenu.setText("File");

        open.setText("Load");
        open.addActionListener(evt -> preImageLoad());
        fileMenu.add(open);

        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(this::saveMenuItemActionPerformed);
        fileMenu.add(saveMenuItem);

        toBMP.setText("Convert to bmp");
        toBMP.addActionListener(this::toBMPActionPerformed);
        fileMenu.add(toBMP);

        closeMenu.setText("Exit");
        closeMenu.addActionListener(this::closeMenuActionPerformed);
        fileMenu.add(closeMenu);

        menuBar.add(fileMenu);

//        aboutBt.setText("About");
//        aboutBt.addActionListener(this::AboutBtActionPerformed);
//        infMenu.add(aboutBt);

        menuBar.add(infMenu);
        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(pFirstImg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pSecondImg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lTrace, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(rbOpenTools)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btExt, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)))
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pStegoChecks, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pStegoChecks, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(rbOpenTools)
                                        .addComponent(btExt))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lTrace, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pFirstImg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pSecondImg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }
    // </editor-fold>

    // Открывает диалог для выбора изображения.
    private void openChooseImageDialog() throws IOException {
        fileChooser1.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnVal = fileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser1.getSelectedFile();
            openImg = ImageIO.read(file);
            nImageWidth = openImg.getWidth(null);
            nImageHeight = openImg.getHeight(null);
        } else {
            System.out.println("File access cancelled by user.");
        }
    }

    // Открывает диалог для сохранения изображения.
    private void openSaveImageDialog(BufferedImage img) throws IOException {
        if (img == null) {
            lTrace.setText("Select a file");
            throw new NullPointerException("Select a file");
        }
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new BmpFilter());
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = new File(fc.getSelectedFile().getAbsolutePath().endsWith(".bmp")
                    ? fc.getSelectedFile().getAbsolutePath()
                    : fc.getSelectedFile().getAbsolutePath() + ".bmp");
            ImageIO.write(img, "bmp", file);
            lTrace.setText("File saved");
        }
    }

    // Часть кода, который используется при загрузке изображения
    private void preImageLoad() {
        lTrace.setText("");
        try {
            openChooseImageDialog();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pix = new int[nImageWidth * nImageHeight];
            oldPix = new int[nImageWidth * nImageHeight];
            pix = Steganography.imgToPix(openImg, nImageWidth, nImageHeight);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (fStart && pix.length != oldPix.length) {
            boundEntropy2 = pix.length;
            boundAvg2 = pix.length;
            intrvAvg = pix.length / 100;
            intrvEntropy = pix.length / 100;
        }
        if (intrvAvg == 0) {
            intrvAvg = pix.length / 100;
        }
        if (intrvEntropy == 0) {
            intrvEntropy = pix.length / 100;
        }
        if (boundEntropy2 == 0) {
            boundEntropy2 = pix.length;
        }
        if (boundEntropy2 > pix.length) {
            boundEntropy2 = pix.length;
        }
        if (boundAvg2 == 0) {
            boundAvg2 = pix.length;
        }
        if (boundAvg2 > pix.length) {
            boundAvg2 = pix.length;
        }
        fStart = true;
        oldPix = pix;
        lTrace.setText("In the image " + pix.length + " pixels");
        forShow = Util.scale(openImg, 475, 255);
        lbFirstImg.setIcon(new ImageIcon(forShow));
    }

    private void btInsertActionPerformed() {
        lTrace.setText("");
        String msg = tpInsText.getText();
        try {
            imgNew = Steganography.ins(pix, msg, numOfPix, nImageWidth, nImageHeight);
        } catch (NullPointerException | UnsupportedEncodingException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        forShow = Util.scale(imgNew, 475, 255);
        lbSecondImg.setIcon(new ImageIcon(forShow));
    }

    private void btExtractActionPerformed(ActionEvent evt) {
        tpInsText.setText("");
        lTrace.setText("");
        tpInsText.setText(Steganography.ext(pix, numOfPix));
    }

    private void AboutBtActionPerformed(ActionEvent evt) {
        try {
            new About().setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btSaveActionPerformed(ActionEvent evt) {
        lTrace.setText("");
        try {
            openSaveImageDialog(imgNew);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void toBMPActionPerformed(ActionEvent evt) {
        lTrace.setText("");
        try {
            openSaveImageDialog(openImg);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeMenuActionPerformed(ActionEvent evt) {
        try {
            new MainFrame().setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }

    private void btExtActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void btInsNumPixActionPerformed(ActionEvent evt) {
        if (tfNumOfPix.getText().equals("")) {
            lTrace.setText("No pixel number entered");
        } else {
            numOfPix = Integer.parseInt(tfNumOfPix.getText());
            lTrace.setText("Pixel number: " + numOfPix);
        }
    }

    private void btInsIntervAvgActionPerformed(ActionEvent evt) {
        if (tfIntervalAvg.getText().equals("")) {
            lTrace.setText("No check interval entered");
        }
        if (Integer.parseInt(tfIntervalAvg.getText()) < 2) {
            lTrace.setText("The check interval should be >= 2");
        } else {
            intrvAvg = Integer.parseInt(tfIntervalAvg.getText());
            lTrace.setText("Check interval: " + intrvAvg);
        }
    }

    private void btGraphAvgActionPerformed(ActionEvent evt) {
        if (pix == null) {
            lTrace.setText("Select a file");
            throw new NullPointerException("Select a file");
        }
        if (boundAvg2 > pix.length) {
            lTrace.setText("Invalid interval. In the image " + pix.length + " pixels");
            throw new NullPointerException("Invalid interval");
        }
        if ((pix.length - boundAvg1) / intrvAvg < 10) {
            lTrace.setText("Check interval too large");
            throw new NullPointerException("Invalid interval");
        }

        ImageInspection.showMovingAverageChart(pix, boundAvg1, boundAvg2, intrvAvg);
    }

    private void btLastBitActionPerformed(ActionEvent evt) {
        if (pix == null) {
            lTrace.setText("Select a file");
            throw new NullPointerException("Select a file");
        }
        imgNew = Steganography.imgLastBits(pix, nImageWidth, nImageHeight);
        forShow = Util.scale(imgNew, 475, 255);
        lbSecondImg.setIcon(new ImageIcon(forShow));
    }

    private void btInsBoundEntActionPerformed(ActionEvent evt) {
        if (tfBoundEnt1.getText().equals("") | tfBoundEnt2.getText().equals("")) {
            lTrace.setText("Enter the bounds of the check interval");
        }
        if ((Integer.parseInt(tfBoundEnt2.getText()) - Integer.parseInt(tfBoundEnt1.getText())) <= 0) {
            lTrace.setText("Wrong borders");
        } else {
            boundEntropy1 = Integer.parseInt(tfBoundEnt1.getText());
            boundEntropy2 = Integer.parseInt(tfBoundEnt2.getText());
            lTrace.setText("Check boundaries from " + boundEntropy1 + " to " + boundEntropy2);
        }
    }

    private void btGraphEntActionPerformed(ActionEvent evt) {
        if (pix == null) {
            lTrace.setText("Select a file");
            throw new NullPointerException("Select a file");
        }
        if (boundEntropy2 > pix.length) {
            lTrace.setText("Invalid interval. In the image " + pix.length + " pixels");
            throw new NullPointerException("Invalid interval");
        }
        if ((boundEntropy2 - boundEntropy1) / intrvEntropy < 10) {
            lTrace.setText("Check interval too large");
            throw new NullPointerException("Invalid interval");
        }
        ImageInspection.showEntropyCalculationChart(pix, boundEntropy1, boundEntropy2, intrvEntropy);
    }

    private void saveMenuItemActionPerformed(ActionEvent evt) {
        lTrace.setText("");
        try {
            openSaveImageDialog(imgNew);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btInsIntervEntActionPerformed(ActionEvent evt) {
        if (tfIntervalEntropy.getText().equals("")) {
            lTrace.setText("No check interval entered");
        }
        if (Integer.parseInt(tfIntervalEntropy.getText()) < 2) {
            lTrace.setText("The check interval must be >= 2");
        } else {
            intrvEntropy = Integer.parseInt(tfIntervalEntropy.getText());
            lTrace.setText("Check interval: " + intrvEntropy);
        }
    }

    private void rbOpenToolsActionPerformed(ActionEvent evt) {
        if (rbOpenTools.isSelected()) {
            changePanel(pStegoChecks, pChecks);
        } else {
            changePanel(pStegoChecks, pStego);
        }
    }

    private void changePanel(JPanel rootPanel, JPanel childPanel) {
        rootPanel.removeAll();
        rootPanel.add(childPanel);
        rootPanel.repaint();
        rootPanel.revalidate();
    }

    private void btSaveCheckActionPerformed(ActionEvent evt) {
        lTrace.setText("");
        try {
            openSaveImageDialog(imgNew);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btInsBoundAvgActionPerformed(ActionEvent evt) {
        if (tfBoundAvg1.getText().equals("") | tfBoundAvg2.getText().equals("")) {
            lTrace.setText("Enter the bounds of the check interval");
        }
        if ((Integer.parseInt(tfBoundAvg2.getText()) - Integer.parseInt(tfBoundAvg1.getText())) <= 0) {
            lTrace.setText("Wrong borders");
        } else {
            boundAvg1 = Integer.parseInt(tfBoundAvg1.getText());
            boundAvg2 = Integer.parseInt(tfBoundAvg2.getText());
            lTrace.setText("Check boundaries from " + boundAvg1 + " to " + boundAvg2);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JMenuItem aboutBt;
    private javax.swing.JButton btExt;
    private javax.swing.JButton btExtract;
    private javax.swing.JButton btGraphAvg;
    private javax.swing.JButton btGraphEnt;
    private javax.swing.JButton btInsBoundAvg;
    private javax.swing.JButton btInsBoundEnt;
    private javax.swing.JButton btInsIntervAvg;
    private javax.swing.JButton btInsIntervEnt;
    private javax.swing.JButton btInsNumPix;
    private javax.swing.JButton btInsert;
    private javax.swing.JButton btLastBit;
    private javax.swing.JButton btLoad;
    private javax.swing.JButton btLoadCheck;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btSaveCheck;
    private javax.swing.JMenuItem closeMenu;
    private javax.swing.JFileChooser fileChooser1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JFileChooser fileSaver;
    private javax.swing.JMenu infMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lTrace;
    private javax.swing.JLabel lbFirstImg;
    private javax.swing.JLabel lbSecondImg;
    private javax.swing.JMenuItem open;
    private javax.swing.JPanel pAvg;
    private javax.swing.JPanel pChecks;
    private javax.swing.JPanel pEntropy;
    private javax.swing.JPanel pFirstImg;
    private javax.swing.JPanel pSecondImg;
    private javax.swing.JPanel pStego;
    private javax.swing.JPanel pStegoChecks;
    private javax.swing.JPanel pVisMethod;
    private javax.swing.JRadioButton rbOpenTools;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JTextField tfBoundAvg1;
    private javax.swing.JTextField tfBoundAvg2;
    private javax.swing.JTextField tfBoundEnt1;
    private javax.swing.JTextField tfBoundEnt2;
    private javax.swing.JTextField tfIntervalAvg;
    private javax.swing.JTextField tfIntervalEntropy;
    private javax.swing.JTextField tfNumOfPix;
    private javax.swing.JMenuItem toBMP;
    private javax.swing.JTextPane tpInsText;
    // End of variables declaration
}
