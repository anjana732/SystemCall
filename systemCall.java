import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
public class systemCall {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("File Management");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the button
        JButton createFile = new JButton("Create File");
        JButton createFolder = new JButton("Create Folder");
        JButton deleteFile = new JButton("Delete File");
	JButton deleteFolder = new JButton("Delete Folder");
	JButton renameFile = new JButton("Rename File");
	JButton renameFolder = new JButton("Rename Folder");
	JButton copyFile = new JButton("Copy File");
	JButton copyFolder = new JButton("Copy Folder");
	JButton moveFile = new JButton("Move File");
	JButton moveFolder = new JButton("Move Folder");


        // Add action listener to the button
        //Action listner for file creation
        createFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");  

                int userSelection = fileChooser.showSaveDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        if (fileToSave.createNewFile()) {
                            System.out.println("File created: " + fileToSave.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "File created: " + fileToSave.getAbsolutePath());
                        } else {
                            System.out.println("File already exists.");
                            JOptionPane.showMessageDialog(null, "File already exists.");
                        }
                    } catch (IOException ex) {
                        System.out.println("An error occurred.");
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "An error occurred.");
                    }
                }
            }
        });
        //Action listner for folder creation
        createFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a directory to save");  
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelection = fileChooser.showSaveDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File dirToSave = fileChooser.getSelectedFile();
                    if (!dirToSave.exists()) {
                        if (dirToSave.mkdir()) {
                            System.out.println("Directory created: " + dirToSave.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "Directory created: " + dirToSave.getAbsolutePath());
                        } else {
                            System.out.println("Failed to create directory.");
                            JOptionPane.showMessageDialog(null, "Failed to create directory.");
                        }
                    } else {
                        System.out.println("Directory already exists.");
                        JOptionPane.showMessageDialog(null, "Directory already exists.");
                    }
                }
            }
        });
	//Action listner for file deletion
	deleteFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to delete");  

                int userSelection = fileChooser.showOpenDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToDelete = fileChooser.getSelectedFile();
                    if (fileToDelete.delete()) {
                        System.out.println("File deleted: " + fileToDelete.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "File deleted: " + fileToDelete.getAbsolutePath());
                    } else {
                        System.out.println("Failed to delete file.");
                        JOptionPane.showMessageDialog(null, "Failed to delete file.");
                    }
                }
            }
        });
        //Action listner for folder deletion
         deleteFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a directory to delete");  
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelection = fileChooser.showOpenDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File dirToDelete = fileChooser.getSelectedFile();
                    if(deleteDirectory(dirToDelete)){
                        System.out.println("Directory deleted: " + dirToDelete.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Directory deleted: " + dirToDelete.getAbsolutePath());
                    } else {
                        System.out.println("Failed to delete directory.");
                        JOptionPane.showMessageDialog(null, "Failed to delete directory.");
                    }
                }
            }

	 boolean deleteDirectory(File directory) {
                File[] allContents = directory.listFiles();
                if (allContents != null) {
                    for (File file : allContents) {
                        deleteDirectory(file);
                    }
                }
                return directory.delete();
            }
        });
	//Action listner for file rename
	 renameFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to rename");  

                int userSelection = fileChooser.showOpenDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File oldFile = fileChooser.getSelectedFile();
                    String newName = JOptionPane.showInputDialog(frame, "Enter new name for the file:");
                    if (newName == null || newName.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Invalid file name, operation cancelled.");
                        return;
                    }

                    File newFile = new File(oldFile.getParent(), newName);
                    if (oldFile.renameTo(newFile)) {
                        System.out.println("File renamed to: " + newFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "File renamed to: " + newFile.getAbsolutePath());
                    } else {
                        System.out.println("Failed to rename file.");
                        JOptionPane.showMessageDialog(null, "Failed to rename file.");
                    }
                }
            }
        });
	//Action listner to rename folder
	renameFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a directory to rename");  
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelection = fileChooser.showOpenDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File oldDirectory = fileChooser.getSelectedFile();
                    String newName = JOptionPane.showInputDialog(frame, "Enter new name for the directory:");
                    if (newName == null || newName.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Invalid directory name, operation cancelled.");
                        return;
                    }

                    File newDirectory = new File(oldDirectory.getParent(), newName);
                    if (oldDirectory.renameTo(newDirectory)) {
                        System.out.println("Directory renamed to: " + newDirectory.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Directory renamed to: " + newDirectory.getAbsolutePath());
                    } else {
                        System.out.println("Failed to rename directory.");
                        JOptionPane.showMessageDialog(null, "Failed to rename directory.");
                    }
                }
            }
        });

	//Action listner for file copy
	   copyFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooserSource = new JFileChooser();
                fileChooserSource.setDialogTitle("Select a file to copy");  

                int userSelectionSource = fileChooserSource.showOpenDialog(frame);

                if (userSelectionSource == JFileChooser.APPROVE_OPTION) {
                    File sourceFile = fileChooserSource.getSelectedFile();
                    
                    JFileChooser fileChooserDest = new JFileChooser();
                    fileChooserDest.setDialogTitle("Select a directory to save the copied file");  
                    fileChooserDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int userSelectionDest = fileChooserDest.showOpenDialog(frame);

                    if(userSelectionDest == JFileChooser.APPROVE_OPTION) {
                        File destDirectory = fileChooserDest.getSelectedFile();
                        File destFile = new File(destDirectory, sourceFile.getName());
                        try {
                            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("File copied to: " + destFile.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "File copied to: " + destFile.getAbsolutePath());
                        } catch (IOException ex) {
                            System.out.println("Failed to copy file.");
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to copy file.");
                        }
                    }
                }
            }
        });
	//Action lister for folder Copy
	 copyFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooserSource = new JFileChooser();
                fileChooserSource.setDialogTitle("Select a directory to copy");  
                fileChooserSource.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelectionSource = fileChooserSource.showOpenDialog(frame);

                if (userSelectionSource == JFileChooser.APPROVE_OPTION) {
                    File sourceDirectory = fileChooserSource.getSelectedFile();
                    
                    JFileChooser fileChooserDest = new JFileChooser();
                    fileChooserDest.setDialogTitle("Select a directory to save the copied directory");  
                    fileChooserDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int userSelectionDest = fileChooserDest.showOpenDialog(frame);

                    if(userSelectionDest == JFileChooser.APPROVE_OPTION) {
                        File destDirectory = fileChooserDest.getSelectedFile();
                        File destPath = new File(destDirectory, sourceDirectory.getName());

                        try {
                            Files.walkFileTree(sourceDirectory.toPath(), new SimpleFileVisitor<Path>() {
                                @Override
                                public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
                                    Files.createDirectories(Paths.get(destPath.getAbsolutePath(), dir.toString().substring(sourceDirectory.getAbsolutePath().length())));
                                    return FileVisitResult.CONTINUE;
                                }

                                @Override
                                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                                    Files.copy(file, Paths.get(destPath.getAbsolutePath(), file.toString().substring(sourceDirectory.getAbsolutePath().length())));
                                    return FileVisitResult.CONTINUE;
                                }
                            });
                            System.out.println("Directory copied to: " + destPath.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "Directory copied to: " + destPath.getAbsolutePath());
                        } catch (IOException ex) {
                            System.out.println("Failed to copy directory.");
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to copy directory.");
                        }
                    }
                }
            }
        });

//Action listner to move file
	moveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooserSource = new JFileChooser();
                fileChooserSource.setDialogTitle("Select a file to move");  

                int userSelectionSource = fileChooserSource.showOpenDialog(frame);

                if (userSelectionSource == JFileChooser.APPROVE_OPTION) {
                    File sourceFile = fileChooserSource.getSelectedFile();
                    
                    JFileChooser fileChooserDest = new JFileChooser();
                    fileChooserDest.setDialogTitle("Select a directory to move the file to");  
                    fileChooserDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int userSelectionDest = fileChooserDest.showOpenDialog(frame);

                    if(userSelectionDest == JFileChooser.APPROVE_OPTION) {
                        File destDirectory = fileChooserDest.getSelectedFile();
                        File destFile = new File(destDirectory, sourceFile.getName());
                        try {
                            Files.move(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("File moved to: " + destFile.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "File moved to: " + destFile.getAbsolutePath());
                        } catch (IOException ex) {
                            System.out.println("Failed to move file.");
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to move file.");
                        }
                    }
                }
            }
        });
       
//Action listner to move folder
 	moveFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooserSource = new JFileChooser();
                fileChooserSource.setDialogTitle("Select a directory to move");  
                fileChooserSource.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelectionSource = fileChooserSource.showOpenDialog(frame);

                if (userSelectionSource == JFileChooser.APPROVE_OPTION) {
                    File sourceDirectory = fileChooserSource.getSelectedFile();
                    
                    JFileChooser fileChooserDest = new JFileChooser();
                    fileChooserDest.setDialogTitle("Select a directory to move the directory to");  
                    fileChooserDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int userSelectionDest = fileChooserDest.showOpenDialog(frame);

                    if(userSelectionDest == JFileChooser.APPROVE_OPTION) {
                        File destDirectory = fileChooserDest.getSelectedFile();
                        File destPath = new File(destDirectory, sourceDirectory.getName());

                        try {
                            Files.move(sourceDirectory.toPath(), destPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Directory moved to: " + destPath.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "Directory moved to: " + destPath.getAbsolutePath());
                        } catch (IOException ex) {
                            System.out.println("Failed to move directory.");
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to move directory.");
                        }
                    }
                }
            }
        });


        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(createFile);
        frame.add(panel);
        frame.setVisible(true);
       // JPanel panel = new JPanel();
      //  panel.setLayout(new FlowLayout());
        panel.add(createFolder);
	panel.add(deleteFile);
	 panel.add(deleteFolder);
	 panel.add(renameFile);
	  panel.add(renameFolder);
	  panel.add(copyFile);
	   panel.add(copyFolder);
	    panel.add(moveFile);
	    panel.add(moveFolder);
       // frame.add(panel);
       // frame.setVisible(true);
    }
}

