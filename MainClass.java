
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
public class MainClass {

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

	
	JPanel pnBtn;//panel para los botones
	JPanel pnTxt; //panel para los campos de entrada

	//creamos una etiqueta para la imagen y el nombre del usuario
	JLabel velocidadLabel;
	JLabel anguloLabel;
	JLabel gravedadLabel;
	
    //declaramos las variables para nuestros botones
	JButton btnNewGame,btnStop,btnReset,btnExit,btnChangeUser,btnInfo,btnMode;
	JTextField txtVel, txtAng, txtGrav;

	// variables para el tiro parabolico
	double x = 70;
	double y = 467;
    double vy0; //velocidad y inicial;
    double vx0; //velocidad x inicial ;
	double velocidad;
	double tiempo = 0;
	double gravedad;
	double angulo;
	double alturaMax;
	double tiempoMax;
	
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

		// creamos el campo de entrada para la velocidad inicial
		velocidadLabel = new JLabel("Velocidad (m/s):");
		velocidadLabel.setHorizontalAlignment(JLabel.CENTER);
		velocidadLabel.setFont(new Font("Helvetica",Font.ITALIC,15));
		velocidadLabel.setForeground(Color.RED);
		pnTxt.add(velocidadLabel,BorderLayout.NORTH);
		txtVel = new JTextField();
		txtVel.setName("velocidad");
		txtVel.setForeground(Color.GREEN);
		txtVel.setFont(new Font("Tahoma", 1, 20));
		txtVel.setHorizontalAlignment(JTextField.CENTER);
		pnTxt.add(txtVel);
		

		// creamos el campo de entrada para el angulo de tiro
		anguloLabel = new JLabel("Angulo (G):");
		anguloLabel.setHorizontalAlignment(JLabel.CENTER);
		anguloLabel.setFont(new Font("Helvetica",Font.ITALIC,15));
		anguloLabel.setForeground(Color.RED);
		pnTxt.add(anguloLabel,BorderLayout.NORTH);
		txtAng = new JTextField();
		txtAng.setName("angulo");
		txtAng.setForeground(Color.GREEN);
		txtAng.setFont(new Font("Tahoma", 1, 20));
		txtAng.setHorizontalAlignment(JTextField.CENTER);
		pnTxt.add(txtAng);

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
			// El objeto se detendrá cuando toque el suelo
			if(y < 480){
				this.moveBall();
				this.repaint();
			}
			try
            {
                Thread.sleep(10);
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
	private void moveBall() {
		// se calcula el tiempo total que deberia tardar la simulación 
		double totalTime = (2.0 * velocidad * Math.sin(angulo)) / gravedad;
        double timeStep = totalTime / 100;
		if(StopPlay){
			// variable que controla el paso del tiempo en la simulación
			tiempo = tiempo + timeStep;
			// Ecuaciones que describen el movimiento parabolico
			x = (velocidad * Math.cos(angulo) * tiempo); // movimiento en el eje x
			x = x + 70;
            y = (velocidad * Math.sin(angulo) * tiempo - (0.5 * gravedad * tiempo * tiempo)); // movimiento en el eje y
			y = 467 - y;
		}
	}

    //dibujamos la simulación en el plano
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Dibujamos la pelota
		g.setColor(Color.RED);
		g.fillOval((int) (x), (int) (y), 20, 20);
		// Dibujamos el suelo
		g.setColor(Color.DARK_GRAY);
		g.fillRect(30, 500, 790, 30);
		// Dibujamos el cañon
		Image img = new ImageIcon(getClass().getResource("res/cannon_2.png")).getImage();
      	g.drawImage(img, 10, 450, this);

		double totalTime = (2.0 * velocidad * Math.sin(angulo)) / gravedad;
        double timeStep = totalTime / 100;
		for (double t = 0; t <= totalTime; t += timeStep) {
        	double x1 = velocidad * Math.cos(angulo) * t;
			x1 = x1 + 70;
        	double y1 = 467 - (velocidad * Math.sin(angulo) * t - (0.5 * gravedad * t * t));
        	// Dibujar linea en la trayectoria
			g.setColor(Color.GREEN);
        	g.fillOval((int) x1, (int) y1, 5, 5);
		}
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
		if(txtVel.getText().toString().length() != 0 &&
			txtAng.getText().toString().length() != 0 &&
			txtGrav.getText().toString().length() != 0){
				velocidad = (Float.parseFloat(txtVel.getText()));
				angulo = Float.parseFloat(txtAng.getText());
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
				x = 70;
				y = 467;
				tiempo = 0;
				txtAng.setText("");
				txtGrav.setText("");
				txtVel.setText("");
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

