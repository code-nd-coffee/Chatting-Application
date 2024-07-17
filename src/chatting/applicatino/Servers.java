package chatting.applicatino;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.border.EmptyBorder;

public class Servers implements ActionListener{
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Servers(){
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0,0, 450, 70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
           @Override
           public void mouseClicked(MouseEvent ae){
               System.exit(0);
           }
        });
        
        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i22 = i11.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i33 = new ImageIcon(i22);
        JLabel profile = new JLabel(i33);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        
        ImageIcon i111 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i222 = i111.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i333 = new ImageIcon(i222);
        JLabel video = new JLabel(i333);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        
        ImageIcon i1111 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i2222 = i1111.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i3333 = new ImageIcon(i2222);
        JLabel phone = new JLabel(i3333);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);
        
        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image ii2 = ii1.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel morevert = new JLabel(ii3);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);
        
        JLabel name = new JLabel("Gaitonde");
        name.setForeground(Color.WHITE);
        name.setBounds(110, 15, 100, 18);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        
        JLabel status = new JLabel("Online");
        status.setForeground(Color.WHITE);
        status.setBounds(110, 38, 100, 12);
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setForeground(Color.WHITE);
        send.setBackground(new Color(7, 94, 84));
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        send.setBounds(320, 655, 123, 40);
        send.addActionListener(this);
        f.add(send);
        
        f.setLayout(null);
        f.setSize(450, 700);
        f.setLocation(200, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
        
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        String out = text.getText();
        JPanel msg = formatLabel(out);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(msg, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical, BorderLayout.PAGE_START);
        try {
            dout.writeUTF(out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        text.setText("");
        
        f.repaint();
        f.invalidate();
        f.validate();
    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style =\"width: 150px\">"+ out +"</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        return panel;
    }
    public static void main(String[]args){
        new Servers();
        
        try{
            ServerSocket skt = new ServerSocket(6969);
            while(true){
                Socket soc = skt.accept();
                DataInputStream din = new DataInputStream(soc.getInputStream());
                dout = new DataOutputStream(soc.getOutputStream());
                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                    
                }
            }
            
        } catch(IOException e){
            e.printStackTrace();
        }
    }
  
}
