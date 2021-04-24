package crud;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Gui {

	private JFrame frame;
	private JTextField txtsmob;
	private JTextField txtsname;
	private JTextField txtscourse;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
		Connect();
		table_load();
	}
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtsid;
	 public void Connect()
	    {
	        try {
	            Class.forName("org.postgresql.Driver");
	            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
	        }
	        catch (ClassNotFoundException ex) 
	        {
	          ex.printStackTrace();
	        }
	        catch (SQLException ex) 
	        {
	        	   ex.printStackTrace();
	        }
	    }
	 public void table_load()
	    {
	    	try 
	    	{  
	    	pst = con.prepareStatement("select * from students");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
	    	}
	    	catch (SQLException e) 
	    	 {
	    		e.printStackTrace();
		  } 
	    }
	    

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.setBounds(100, 100, 652, 404);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Management");
		lblNewLabel.setBounds(96, 0, 278, 66);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 49, 332, 290);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Student Name");
		lblNewLabel_1.setBounds(22, 43, 78, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mobile");
		lblNewLabel_1_1.setBounds(22, 80, 78, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Course");
		lblNewLabel_1_1_1.setBounds(22, 118, 78, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sname,mobile,course;
				sname = txtsname.getText();
				mobile = txtsmob.getText();
				course = txtscourse.getText();
							
				 try {
					pst = con.prepareStatement("insert into students(name,mobile,course)values(?,?,?)");
					pst.setString(1, sname);
					pst.setString(2, mobile);
					pst.setString(3, course);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
					table_load();
						           
					
					txtsname.setText("");
					txtsmob.setText("");
					txtscourse.setText("");
					txtsname.requestFocus();
				   }
			 
				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 158, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	String sname,mobile,course,sid;
				
				
				sname = txtsname.getText();
				mobile = txtsmob.getText();
				course = txtscourse.getText();
				sid  = txtsid.getText();
				
				 try {
						pst = con.prepareStatement("update students set name= ?,mobile=?,course=? where name =?");
						pst.setString(1, sname);
			            pst.setString(2, mobile);
			            pst.setString(3, course);
			            pst.setString(4, sid);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Update!!!!!");
			            table_load();
			           
			            txtsname.setText("");
			            txtsmob.setText("");
			            txtscourse.setText("");
			            txtsname.requestFocus();
					}
 
		            catch (SQLException e1) {
						
						e1.printStackTrace();
					}
			}
		});
		btnEdit.setBounds(109, 158, 89, 23);
		panel.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sid;
				sid  = txtsid.getText();
				
				 try {
						pst = con.prepareStatement("delete from students where name =?");
				
			            pst.setString(1, sid);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
			            table_load();
			           
			            txtsname.setText("");
			            txtsmob.setText("");
			            txtscourse.setText("");
			            txtsname.requestFocus();
					}
	 
		            catch (SQLException e1) {
						
						e1.printStackTrace();
		            }
			
			}
		});
		btnDelete.setBounds(210, 158, 89, 23);
		panel.add(btnDelete);
		
		txtsmob = new JTextField();
		txtsmob.setBounds(126, 77, 129, 20);
		panel.add(txtsmob);
		txtsmob.setColumns(10);
		
		txtsname = new JTextField();
		txtsname.setColumns(10);
		txtsname.setBounds(126, 40, 129, 20);
		panel.add(txtsname);
		
		txtscourse = new JTextField();
		txtscourse.setColumns(10);
		txtscourse.setBounds(126, 115, 129, 20);
		panel.add(txtscourse);
		
		JLabel lblNewLabel_2 = new JLabel("Search by Name");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 195, 109, 27);
		panel.add(lblNewLabel_2);
		
		txtsid = new JTextField();
		txtsid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				 try {
			          
			            String id = txtsid.getText();

			                pst = con.prepareStatement("select name,mobile,course from students where name = ?");
			                pst.setString(1, id);
			                ResultSet rs = pst.executeQuery();

			            if(rs.next()==true)
			            {
			              
			                String name = rs.getString(1);
			                String mobile = rs.getString(2);
			                String course = rs.getString(3);
			                
			                txtsname.setText(name);
			                txtsmob.setText(mobile);
			                txtscourse.setText(course);
			                
			                
			            }   
			            else
			            {
			            	txtsname.setText("");
			            	txtsmob.setText("");
			                txtscourse.setText("");
			                 
			            }
			         
				 }
				 catch (SQLException ex) {
			           
			        }
			}
			
		});
		txtsid.setBounds(10, 233, 107, 26);
		panel.add(txtsid);
		txtsid.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.setForeground(new Color(255, 0, 0));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBounds(190, 233, 109, 37);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Clear From");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		btnNewButton_2.setBounds(190, 192, 109, 32);
		panel.add(btnNewButton_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(344, 63, 243, 210);
		frame.getContentPane().add(scrollPane_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
