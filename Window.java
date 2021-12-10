import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Window {
    
    JFrame frame;
    JPanel canvas;

    JPanel filePanel;
    JPanel displayPanel;
    JPanel displayTopPanel;
    JPanel displayBottomPanel;
    JPanel displayTextPanel;
    JPanel operationsPanel;
    JPanel bottomPanel;
    JPanel entryPanel;
    JPanel utilityPanel;

    JButton newFileButton;
    JButton loadFileButton;
    JButton viewLogButton;

    JLabel listPreviewLabel;
    JTextArea textArea;
    JScrollPane scrollPane;

    DefaultListModel<String> entryListModel;
    JList<String> entriesJList;

    JButton addEntryButton;
    JButton deleteEntryButton;
    JButton searchEntryButton;
    JButton modifyEntryButton;
    JButton saveFileButton;
    JButton deleteFileButton;
    
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JLabel rollLabel;
    JLabel marksLabel;
    JLabel gradeLabel;
    JLabel indexLabel;
    JLabel statusLabel;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField rollField;
    JTextField marksField;
    JTextField gradeField;
    JTextField indexField;
    JButton undoButton;
    JButton clearTextsButton;

    String currentlyViewingMessage;
    String statusString;
    String textAreaString;
    String firstNameString;
    String lastNameString;
    String rollString;
    String marksString;
    String gradeString;
    String indexString;

    final int windowHeight = 800;
    final int windowWidth = 1200;

    boolean isFileSelected = false;


    Window()
    {
        entryListModel = new DefaultListModel<>();
        entriesJList = new JList<>(entryListModel);
        entriesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        entriesJList.setVisibleRowCount(20);

        statusString = "STATUS: Application working correctly";
        statusLabel = new JLabel(statusString,SwingConstants.CENTER);
        statusLabel.setFont(new Font("Calibri",Font.PLAIN,16));

        currentlyViewingMessage = "CURRENTLY VIEWING: No file selected";
        listPreviewLabel = new JLabel(currentlyViewingMessage,SwingConstants.CENTER);
        listPreviewLabel.setFont(new Font("Calibri",Font.PLAIN,20));
        
        newFileButton = new JButton("CREATE NEW DATABASE FILE");
        loadFileButton = new JButton("LOAD DATABASE FILE");
        viewLogButton = new JButton("VIEW LOG FILE");
        undoButton = new JButton("UNDO LAST ACTION");
        clearTextsButton = new JButton("CLEAR ALL INPUT TEXTS");

        firstNameString = "";
        lastNameString = "";
        rollString = "";
        marksString = "";
        gradeString = "";
        indexString = "";
        textAreaString = "";

        firstNameLabel = new JLabel("FIRST NAME:");
        lastNameLabel = new JLabel("LAST NAME:");
        rollLabel = new JLabel("ROLL:");
        marksLabel = new JLabel("MARKS:");
        gradeLabel = new JLabel("GRADE:");
        indexLabel = new JLabel("INDEX:");

        firstNameField = new JTextField(firstNameString);
        lastNameField = new JTextField(lastNameString);
        rollField = new JTextField(rollString);
        marksField = new JTextField(marksString);
        gradeField = new JTextField(gradeString);
        indexField = new JTextField(indexString);
        
        textArea = new JTextArea(textAreaString);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(entriesJList,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        addEntryButton = new JButton("ADD ENTRY");
        deleteEntryButton = new JButton("DELETE ENTRY");
        searchEntryButton = new JButton("SEARCH ENTRY");
        modifyEntryButton = new JButton("MODIFY ENTRY");
        saveFileButton = new JButton("SAVE CURRENT FILE");
        deleteFileButton = new JButton("DELETE CURRENT FILE");

        filePanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.DARK_GRAY);
            }
        };
        filePanel.setLayout(new GridLayout(1,3,100,0));
        filePanel.setPreferredSize(new Dimension(windowWidth,windowHeight/20));
        filePanel.add(newFileButton);
        filePanel.add(loadFileButton);
        filePanel.add(viewLogButton);
        filePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        displayTopPanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.LIGHT_GRAY);
            }
        };
        displayTopPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        displayTopPanel.add(listPreviewLabel);
        displayTopPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        displayBottomPanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.LIGHT_GRAY);
            }
        };
        displayBottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        displayBottomPanel.add(statusLabel);
        displayBottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        displayPanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.DARK_GRAY);
            }
        };
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayTopPanel,BorderLayout.NORTH);
        displayPanel.add(scrollPane,BorderLayout.CENTER);
        displayPanel.add(displayBottomPanel,BorderLayout.SOUTH);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        operationsPanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.DARK_GRAY);
            }
        };
        operationsPanel.setLayout(new GridLayout(1,4,50,5));
        operationsPanel.add(addEntryButton);
        operationsPanel.add(deleteEntryButton);
        operationsPanel.add(modifyEntryButton);
        operationsPanel.add(searchEntryButton); 
        operationsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        entryPanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.white);
            }
        };
        entryPanel.setLayout(new GridLayout(3,4,10,10));
        entryPanel.add(firstNameLabel);
        entryPanel.add(firstNameField);
        entryPanel.add(lastNameLabel);
        entryPanel.add(lastNameField);
        entryPanel.add(rollLabel);
        entryPanel.add(rollField);
        entryPanel.add(marksLabel);
        entryPanel.add(marksField);
        entryPanel.add(gradeLabel);
        entryPanel.add(gradeField);
        entryPanel.add(indexLabel);
        entryPanel.add(indexField);

        firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        rollLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        marksLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gradeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        indexLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        utilityPanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.white);
            }
        };
        utilityPanel.setLayout(new GridLayout(4,1,5,5));
        utilityPanel.add(clearTextsButton);
        utilityPanel.add(undoButton);
        utilityPanel.add(saveFileButton);
        utilityPanel.add(deleteFileButton);

        bottomPanel = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.white);
            }
        };
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.LINE_AXIS));
        bottomPanel.add(entryPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(20,0)));
        bottomPanel.add(utilityPanel);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        canvas = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.WHITE);
            }
        };

        canvas.setLayout(new BoxLayout(canvas,BoxLayout.Y_AXIS));
        canvas.add(filePanel);
        canvas.add(displayPanel);
        canvas.add(operationsPanel);
        canvas.add(bottomPanel);
        canvas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        frame = new JFrame("Students Database Application");
        frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centerFrame(frame);
        frame.setContentPane(canvas);
        frame.pack();

        disableOperations();


        clearTextsButton.addActionListener(e -> 
        {
            clearEntryTexts();
            setDefaultAllComponents();
        });

        newFileButton.addActionListener(e ->
        {
            if(!isFileSelected)
            {
                fileSelected();
                enableOperations();
            }
        });

        loadFileButton.addActionListener(e ->
        {
            if(!isFileSelected)
            {
                fileSelected();
                enableOperations();
            }
        });

        deleteFileButton.addActionListener(e ->
        {
            if(isFileSelected)
            {
                fileNotSelected();
                disableOperations();
            }  
        });

    }

    public void enableOperations()
    {
        addEntryButton.setEnabled(true);
        deleteEntryButton.setEnabled(true);
        searchEntryButton.setEnabled(true);
        modifyEntryButton.setEnabled(true);
        saveFileButton.setEnabled(true);
        deleteFileButton.setEnabled(true);
        undoButton.setEnabled(true);
        clearTextsButton.setEnabled(true);
    }

    public void disableOperations()
    {
        addEntryButton.setEnabled(false);
        deleteEntryButton.setEnabled(false);
        searchEntryButton.setEnabled(false);
        modifyEntryButton.setEnabled(false);
        saveFileButton.setEnabled(false);
        deleteFileButton.setEnabled(false);
        undoButton.setEnabled(false);
        clearTextsButton.setEnabled(false);
    }

    public void fileSelected()
    {
        isFileSelected = true;
    }

    public void fileNotSelected()
    {
        isFileSelected = false;
    }

    public boolean isNoneSelected()
    {
        return entriesJList.isSelectionEmpty();
    }

    public void setDefaultAllComponents()
    {
        listPreviewLabel.setText(currentlyViewingMessage);
        textArea.setText(textAreaString);
        firstNameField.setText(firstNameString);
        lastNameField.setText(lastNameString);
        rollField.setText(rollString);
        marksField.setText(marksString);
        gradeField.setText(gradeString);
        indexField.setText(indexString);
        statusLabel.setText(statusString);
    }

    public void getInputStrings()
    {
        firstNameString = firstNameField.getText();
        lastNameString = lastNameField.getText();
        rollString = rollField.getText();
        marksString = marksField.getText();
        gradeString = gradeField.getText();
        indexString = indexField.getText();
    }

    public void clearEntryTexts()
    {
        firstNameString = "";
        lastNameString = "";
        rollString = "";
        marksString = "";
        gradeString = "";
        indexString = "";
    }

    private void centerFrame(JFrame f)
    {
        Dimension windowSize = frame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;
        f.setLocation(dx,dy);
    }
}
