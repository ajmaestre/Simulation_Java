
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;



// CLASE PRINCIPAL
public class Pendulo {

	public static void main(String[] args) {
		Panel panel = new Panel();
		try {
             
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}


// PANEL DE VISUALIZACION DEL JUEGO
class Panel extends JPanel{
	
	//creamos un objeto frame para el contenedor
	JFrame jf;
    JLabel clock;
    JPanel paux;
    int count = 0;
	boolean StopPlay = false;
    boolean change = false;

	
	JPanel pnBtn;//panel para los botones
	JPanel pnTxt; //panel para los campos de entrada

	//creamos una etiqueta para la imagen y el nombre del usuario
	JLabel longitudLabel;
	JLabel gravedadLabel;
	
    //declaramos las variables para nuestros botones
	JButton btnNewGame,btnStop,btnReset,btnExit;
	JTextField txtLong, txtGrav;

	// variables para el tiro parabolico
	double x = 475;
	double y = 350;
	double periodo = 0;
	double frecuencia;
	double gravedad;
	double longitud = 350;
	
	Panel() {

		//creamos el contenedor
		jf = new JFrame();
		jf.setLayout(new BorderLayout());
		
		//creamos el panel auxiliar y lo agregamos al contenedor
		paux = new JPanel();
		paux.setLayout(new BoxLayout(paux, BoxLayout.Y_AXIS));
		paux.setBorder(new MatteBorder(10,10,10,10,Color.WHITE));
		paux.setPreferredSize(new Dimension(200,500));
		jf.add(paux, BorderLayout.EAST);

        //creamos el panel para los campos de entrada y lo agregamos al panel auxiliar
		pnTxt = new JPanel();
		pnTxt.setLayout(new GridLayout(6,1));
		pnTxt.setBorder(new MatteBorder(7,7,7,7,Color.WHITE));
		paux.add(pnTxt);

		// creamos el campo de entrada para la longitud del péndulo
		longitudLabel = new JLabel("Longitud (m):");
		longitudLabel.setHorizontalAlignment(JLabel.CENTER);
		longitudLabel.setFont(new Font("Helvetica",Font.ITALIC,15));
		longitudLabel.setForeground(Color.RED);
		pnTxt.add(longitudLabel,BorderLayout.NORTH);
		txtLong = new JTextField();
		txtLong.setName("longitud");
		txtLong.setForeground(Color.GREEN);
		txtLong.setFont(new Font("Tahoma", 1, 20));
		txtLong.setHorizontalAlignment(JTextField.CENTER);
		pnTxt.add(txtLong);
		

		// creamos el campo de entrada para la gravedad
		gravedadLabel = new JLabel("Gravedad (m/s^2): ");
		gravedadLabel.setHorizontalAlignment(JLabel.CENTER);
		gravedadLabel.setFont(new Font("Helvetica",Font.ITALIC,15));
		gravedadLabel.setForeground(Color.RED);
		pnTxt.add(gravedadLabel,BorderLayout.NORTH);
		txtGrav = new JTextField();
		txtGrav.setName("gravedad");
		txtGrav.setForeground(Color.GREEN);
		txtGrav.setFont(new Font("Tahoma", 1, 20));
		txtGrav.setHorizontalAlignment(JTextField.CENTER);
		pnTxt.add(txtGrav);
		

        //creamos el panel para los botones y lo agregamos al panel auxiliar
		pnBtn = new JPanel();
		pnBtn.setLayout(new GridLayout(2,2));
		pnBtn.setBorder(new MatteBorder(10,10,10,10,Color.WHITE));
		paux.add(pnBtn);
		
		//creamos el boton nuevo juego y lo agregamos al panel de botones
		btnNewGame = new JButton();
		btnNewGame.setIcon(new ImageIcon(getClass().getResource("res/game3.png")));
		btnNewGame.setFocusPainted(false);
		btnNewGame.setContentAreaFilled(false);
		pnBtn.add(btnNewGame);
        newGame();
		
		//boton mejores juegos
		btnStop = new JButton();
		btnStop.setIcon(new ImageIcon(getClass().getResource("res/newG.png")));
		btnStop.setFocusPainted(false);
		btnStop.setContentAreaFilled(false);
		pnBtn.add(btnStop);
        stopGame();
		
		//boton usuario
		btnReset = new JButton();
		btnReset.setIcon(new ImageIcon(getClass().getResource("res/re1.png")));
		btnReset.setFocusPainted(false);
		btnReset.setContentAreaFilled(false);
		pnBtn.add(btnReset);
		resetGame();
		
		//boton salir
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(getClass().getResource("res/exit.png")));
		btnExit.setFocusPainted(false);
		btnExit.setContentAreaFilled(false);
		pnBtn.add(btnExit);
		exitGame();

		jf.add(this);

        //establecemos los parametros del contenedor
		jf.pack();
		jf.setSize(1080, 600);
		jf.setResizable(false);
		jf.setBackground(Color.WHITE);
		Image img2 = new ImageIcon(getClass().getResource("res/sudo2.png")).getImage();
		jf.setIconImage(img2);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	
		// iniciamos el ciclo del hilo que controlara la simulación
		while (true) {
			this.movePendulo();
			this.repaint();
			try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
		}
	}

	// Mensaje de advertencia para rellenar los campos de texto de las variables
	public void boxMessage(String msg){
		JOptionPane.showMessageDialog(null,
                msg,
                "Advertencia",
                JOptionPane.INFORMATION_MESSAGE);
	}

	// Función para generar el movimiento parabolico del objeto
	private void movePendulo() {
		
		if(StopPlay){
            periodo = 2*Math.PI*Math.sqrt(longitud/gravedad); 
			frecuencia = Math.sqrt(gravedad/longitud); 
			if(x >= 645){
                change = true;
            }else if(x <= 305){
                change = false;
            }
            if(change){
                x -= frecuencia;
            }else{
                x += frecuencia;
            }
		}
	}

    //dibujamos la simulación en el plano
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Dibujamos la pelota
		g.setColor(Color.GREEN);
		g.fillOval((int) (x - 15), calcularEjeY((int) x, (int) longitud) - 15, 30, 30);
		// Dibujamos el soporte del pendulo
		g.setColor(Color.DARK_GRAY);
		g.fillRect(300, 100, 350, 30);
        // Dibujamos la cuerda
        g.setColor(Color.RED);
        g.drawLine(475, 130, (int) x, calcularEjeY((int) x, (int) longitud));
	}

    public int calcularEjeY(int ejeX, int r){
        int ejeY = (int) Math.sqrt(Math.abs((r*r) - Math.pow(ejeX - 475, 2))) + 130;
        return ejeY;
    }

    //iniciamos una nueva simulacion
	public void newGame() {
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initSimulation();
			}
		});
	}

	// Capturamos las variables de entrada y iniciamos la simulación
	public void initSimulation(){
		if(txtLong.getText().toString().length() != 0 &&
			txtGrav.getText().toString().length() != 0){
				longitud = (Float.parseFloat(txtLong.getText()));
				gravedad = (Float.parseFloat(txtGrav.getText()));
				StopPlay = true;
		}else{
			boxMessage("Debe rellenar todos los campos de las variables");
		}
	}

    //Pausamos la simulación
	public void stopGame() {
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StopPlay = false;
			}
		});
	}

	//Reiniciamos la simulacion
	public void resetGame() {
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StopPlay = false;
				x = 475;
				y = 467;
				periodo = 0;
				frecuencia = 0;
				txtGrav.setText("");
				txtLong.setText("");
			}
		});
	}

	//Salimos de la simulación
	public void exitGame() {
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
	
	//metodo para salir de la simulación
	public void exit() {
		System.exit(0);
	}
	
}

