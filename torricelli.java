
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
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

import java.text.DecimalFormat;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


// CLASE PRINCIPAL
public class Torricelli {

	public static void main(String[] args) {
		Panel panel = new Panel();
		try {
             
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}


// PANEL DE VISUALIZACION DE LA SIMULACIÓN
class Panel extends JPanel{
	
	//creamos un objeto frame para el contenedor
	JFrame jf;
    JLabel clock, clockLabel, speed, speedLabel, level, levelFldLb;
	JLabel areaOrificioLb, alturaTanqueLb, areaTanqueLb, nivelFluidoLb, velocidadSalidaLb;
	JLabel speedPipeLb, areaPipeLb;
    JPanel paux, pnGame;
    int count = 0;
	boolean StopPlay = false;
	boolean DrawNOW = false;
	
	JPanel pnBtn;//panel para los botones
	JPanel pnCtrl; //panel para los campos de entrada
    JPanel pnInfo; //panel para la información de la simulación
	
    //declaramos las variables para nuestros botones
	JButton btnNewGame,btnStop,btnReset,btnExit,btnDraw,btnRemove,btnInfo,btnTor,btnBal;

	//declaramos las variables para nuestros campos de entrada
	JTextField areaOrificio, alturaTanque, areaTanque, nivelFluido, velocidadSalida;
	JTextField speedPipe, areaPipe;

	
	double g = 9.8;
	double c = 0.6;
	double speedTub = 20;
	double areaTub = 20;
	double areaPipe_ = Math.pow(areaTub/2, 2)*Math.PI;
	double speedOf = 30;
	double areaOf = 30;
	double areaOf_ = Math.pow(areaOf/2, 2)*Math.PI;
	double alturaTq = 200;
	double areaTq = 200;
	double areaTq_ = Math.pow(areaTq/2, 2)*Math.PI;
	double nivelFld = 190;
	double alturaOf = 10;
	double volume_I = areaTq_*nivelFld;
	double flujo_i = areaPipe_*speedTub;
	double flujo_s = areaOf_*speedFld_T();
	double time = 0;
	double timeOf = 0;
	double timeTub = 0;
	DecimalFormat formating = new DecimalFormat("####.##");

	//contadores para el cronometro
    private int contSClock = 0;//segundos
    private int contMClock = 0;//minutos
    private int contHClock = 0;//horas
	private String ms = "00", ss = "00", mm = "00";

	private boolean torricelli = false;
	
	Panel() {

		//creamos el contenedor
		jf = new JFrame();
		jf.setLayout(new BorderLayout());
		
		//creamos el panel auxiliar y lo agregamos al contenedor
		GridBagConstraints rest_pane = new GridBagConstraints();
		GridBagLayout gridBag_pane = new GridBagLayout();
		rest_pane.fill = GridBagConstraints.HORIZONTAL;
		paux = new JPanel();
		paux.setLayout(gridBag_pane);
		paux.setBorder(new MatteBorder(10,10,10,10,Color.WHITE));
		paux.setPreferredSize(new Dimension(340,500));
		jf.add(paux, BorderLayout.EAST);

        //creamos el panel para la información de la simulación y lo anexamos al panel auxiliar
		pnInfo = new JPanel();
		pnInfo.setLayout(new GridLayout(3,2));
		pnInfo.setBorder(new MatteBorder(20,0,20,0,Color.WHITE));
        pnInfo.setBackground(Color.LIGHT_GRAY);
		rest_pane.gridx = 0;
		rest_pane.gridy = 0;
		rest_pane.weightx = 1;
		gridBag_pane.setConstraints(pnInfo, rest_pane);
		paux.add(pnInfo);

        clockLabel = new JLabel("Timer: ");
        clockLabel.setHorizontalAlignment(JLabel.LEFT);
		clockLabel.setFont(new Font("Helvetica",Font.BOLD,15));
		clockLabel.setForeground(Color.BLACK);
        clockLabel.setBorder(new MatteBorder(5,10,5,5,Color.LIGHT_GRAY));
        pnInfo.add(clockLabel);

        clock = new JLabel(mm + ":" + ss + ":" + ms + " (0 s)");
        clock.setHorizontalAlignment(JLabel.LEFT);
		clock.setFont(new Font("Helvetica",Font.BOLD,15));
		clock.setForeground(Color.RED);
        clock.setBorder(new MatteBorder(5,5,5,5,Color.LIGHT_GRAY));
        pnInfo.add(clock);

        speedLabel = new JLabel("Velocidad de salida: ");
        speedLabel.setHorizontalAlignment(JLabel.LEFT);
		speedLabel.setFont(new Font("Helvetica",Font.BOLD,15));
		speedLabel.setForeground(Color.BLACK);
        speedLabel.setBorder(new MatteBorder(5,10,5,5,Color.LIGHT_GRAY));
        pnInfo.add(speedLabel);

        speed = new JLabel(String.valueOf(formating.format(0.00)) + " cm/s" + " (" + formating.format(0.00) + " m/s)");
        speed.setHorizontalAlignment(JLabel.LEFT);
		speed.setFont(new Font("Helvetica",Font.BOLD,15));
		speed.setForeground(Color.RED);
        speed.setBorder(new MatteBorder(5,5,5,5,Color.LIGHT_GRAY));
        pnInfo.add(speed);

		levelFldLb = new JLabel("Nivel del fluido: ");
        levelFldLb.setHorizontalAlignment(JLabel.LEFT);
		levelFldLb.setFont(new Font("Helvetica",Font.BOLD,15));
		levelFldLb.setForeground(Color.BLACK);
        levelFldLb.setBorder(new MatteBorder(5,10,5,5,Color.LIGHT_GRAY));
        pnInfo.add(levelFldLb);

        level = new JLabel(String.valueOf(Math.round(0.00)) + " cm" + " (" + formating.format(Math.round(0.00)/100) + " m)");
        level.setHorizontalAlignment(JLabel.LEFT);
		level.setFont(new Font("Helvetica",Font.BOLD,15));
		level.setForeground(Color.RED);
        level.setBorder(new MatteBorder(5,5,5,5,Color.LIGHT_GRAY));
        pnInfo.add(level);

        //creamos el panel para los campos de entrada
		GridBagConstraints restricciones = new GridBagConstraints();
		GridBagLayout gridBag = new GridBagLayout();
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		pnCtrl = new JPanel();
		pnCtrl.setLayout(gridBag);
		pnCtrl.setBorder(new MatteBorder(20,10,20,10,Color.LIGHT_GRAY));
        pnCtrl.setBackground(Color.LIGHT_GRAY);
		rest_pane.gridx = 0;
		rest_pane.gridy = 1;
		rest_pane.weightx = 1;
		gridBag_pane.setConstraints(pnCtrl, rest_pane);
		paux.add(pnCtrl);

		// creamos el campo de entrada para el area del orificio de salida
		areaOrificioLb = new JLabel("Diametro del orificio (cm):");
		areaOrificioLb.setHorizontalAlignment(JLabel.CENTER);
		areaOrificioLb.setFont(new Font("Helvetica",Font.BOLD,15));
		areaOrificioLb.setForeground(Color.RED);
		areaOrificioLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 0;
		gridBag.setConstraints(areaOrificioLb, restricciones);
		pnCtrl.add(areaOrificioLb);
		areaOrificio = new JTextField();
		areaOrificio.setName("areaOrificio");
		areaOrificio.setForeground(Color.BLACK);
		areaOrificio.setFont(new Font("Tahoma", 1, 15));
		areaOrificio.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		areaOrificio.setHorizontalAlignment(JTextField.CENTER);
		restricciones.gridx = 2;
		restricciones.gridy = 0;
		restricciones.weightx = 1;
		gridBag.setConstraints(areaOrificio, restricciones);
		pnCtrl.add(areaOrificio);


		// creamos el campo de entrada para el nivel inicial del fluido
		velocidadSalidaLb = new JLabel("Velocidad de salida (cm/s):");
		velocidadSalidaLb.setHorizontalAlignment(JLabel.CENTER);
		velocidadSalidaLb.setFont(new Font("Helvetica",Font.BOLD,15));
		velocidadSalidaLb.setForeground(Color.RED);
		velocidadSalidaLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 1;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(velocidadSalidaLb, restricciones);
		pnCtrl.add(velocidadSalidaLb);
		velocidadSalida = new JTextField();
		velocidadSalida.setName("densidadFluido");
		velocidadSalida.setForeground(Color.BLACK);
		velocidadSalida.setFont(new Font("Tahoma", 1, 15));
		velocidadSalida.setHorizontalAlignment(JTextField.CENTER);
		velocidadSalida.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.gridx = 2;
		restricciones.gridy = 1;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(velocidadSalida, restricciones);
		pnCtrl.add(velocidadSalida);


		// creamos el campo de entrada para la altura del tanque
		alturaTanqueLb = new JLabel("Altura del tanque (cm):");
		alturaTanqueLb.setHorizontalAlignment(JLabel.CENTER);
		alturaTanqueLb.setFont(new Font("Helvetica",Font.BOLD,15));
		alturaTanqueLb.setForeground(Color.RED);
		alturaTanqueLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		restricciones.gridx = 1;
		restricciones.gridy = 2;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(alturaTanqueLb, restricciones);
		pnCtrl.add(alturaTanqueLb);
		alturaTanque = new JTextField();
		alturaTanque.setName("alturaTanque");
		alturaTanque.setForeground(Color.BLACK);
		alturaTanque.setFont(new Font("Tahoma", 1, 15));
		alturaTanque.setHorizontalAlignment(JTextField.CENTER);
		alturaTanque.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		restricciones.gridx = 2;
		restricciones.gridy = 2;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(alturaTanque, restricciones);
		pnCtrl.add(alturaTanque);


        // creamos el campo de entrada para el area del tanque
		areaTanqueLb = new JLabel("Diametro del tanque (cm):");
		areaTanqueLb.setHorizontalAlignment(JLabel.CENTER);
		areaTanqueLb.setFont(new Font("Helvetica",Font.BOLD,15));
		areaTanqueLb.setForeground(Color.RED);
		areaTanqueLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 3;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(areaTanqueLb, restricciones);
		pnCtrl.add(areaTanqueLb);
		areaTanque = new JTextField();
		areaTanque.setName("areaTanque");
		areaTanque.setForeground(Color.BLACK);
		areaTanque.setFont(new Font("Tahoma", 1, 15));
		areaTanque.setHorizontalAlignment(JTextField.CENTER);
		areaTanque.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.gridx = 2;
		restricciones.gridy = 3;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(areaTanque, restricciones);
		pnCtrl.add(areaTanque);


        // creamos el campo de entrada para el nivel inicial del fluido
		nivelFluidoLb = new JLabel("Altura del fluido (cm):");
		nivelFluidoLb.setHorizontalAlignment(JLabel.CENTER);
		nivelFluidoLb.setFont(new Font("Helvetica",Font.BOLD,15));
		nivelFluidoLb.setForeground(Color.RED);
		nivelFluidoLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 4;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(nivelFluidoLb, restricciones);
		pnCtrl.add(nivelFluidoLb);
		nivelFluido = new JTextField();
		nivelFluido.setName("nivelFluido");
		nivelFluido.setForeground(Color.BLACK);
		nivelFluido.setFont(new Font("Tahoma", 1, 15));
		nivelFluido.setHorizontalAlignment(JTextField.CENTER);
		nivelFluido.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.gridx = 2;
		restricciones.gridy = 4;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(nivelFluido, restricciones);
		pnCtrl.add(nivelFluido);


		// creamos el campo de entrada para la velocidad de salida del fluido de la tuberia
		speedPipeLb = new JLabel("Velocidad de entrada (cm/s):");
		speedPipeLb.setHorizontalAlignment(JLabel.CENTER);
		speedPipeLb.setFont(new Font("Helvetica",Font.BOLD,15));
		speedPipeLb.setForeground(Color.RED);
		speedPipeLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 5;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(speedPipeLb, restricciones);
		pnCtrl.add(speedPipeLb);
		speedPipe = new JTextField();
		speedPipe.setName("velocidadPipe");
		speedPipe.setForeground(Color.BLACK);
		speedPipe.setFont(new Font("Tahoma", 1, 15));
		speedPipe.setHorizontalAlignment(JTextField.CENTER);
		speedPipe.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.gridx = 2;
		restricciones.gridy = 5;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(speedPipe, restricciones);
		pnCtrl.add(speedPipe);

		
		// creamos el campo de entrada para el diametro de la tuberia
		areaPipeLb = new JLabel("Diametro de la tuberia (cm):");
		areaPipeLb.setHorizontalAlignment(JLabel.CENTER);
		areaPipeLb.setFont(new Font("Helvetica",Font.BOLD,15));
		areaPipeLb.setForeground(Color.RED);
		areaPipeLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 6;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(areaPipeLb, restricciones);
		pnCtrl.add(areaPipeLb);
		areaPipe = new JTextField();
		areaPipe.setName("areaPipe");
		areaPipe.setForeground(Color.BLACK);
		areaPipe.setFont(new Font("Tahoma", 1, 15));
		areaPipe.setHorizontalAlignment(JTextField.CENTER);
		areaPipe.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.gridx = 2;
		restricciones.gridy = 6;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(areaPipe, restricciones);
		pnCtrl.add(areaPipe);

		
        //creamos el panel para los botones y lo agregamos al panel auxiliar
		pnBtn = new JPanel();
		pnBtn.setLayout(new GridLayout(2,4));
		pnBtn.setBorder(new MatteBorder(20,0,20,0,Color.WHITE));
        pnBtn.setBackground(Color.LIGHT_GRAY);
		rest_pane.gridx = 0;
		rest_pane.gridy = 3;
		rest_pane.weightx = 1;
		gridBag_pane.setConstraints(pnBtn, rest_pane);
		paux.add(pnBtn);
		
				
		//creamos el boton para dibujar el tanque y lo agregamos al panel de botones
		btnDraw = new JButton();
		btnDraw.setIcon(new ImageIcon(getClass().getResource("img_game/draw.png")));
		btnDraw.setFocusPainted(false);
		btnDraw.setContentAreaFilled(false);
		pnBtn.add(btnDraw);
		drawNew();

		//creamos el boton nuevo juego y lo agregamos al panel de botones
		btnNewGame = new JButton();
		btnNewGame.setIcon(new ImageIcon(getClass().getResource("img_game/play.png")));
		btnNewGame.setFocusPainted(false);
		btnNewGame.setContentAreaFilled(false);
		pnBtn.add(btnNewGame);
        newGame();

		//boton info
		btnInfo = new JButton();
		btnInfo.setIcon(new ImageIcon(getClass().getResource("img_game/info_2.png")));
		btnInfo.setFocusPainted(false);
		btnInfo.setContentAreaFilled(false);
		pnBtn.add(btnInfo);
		showInfo();

		//boton para cambiar a la simulación de torricelli
		btnTor = new JButton();
		btnTor.setIcon(new ImageIcon(getClass().getResource("img_game/tor.png")));
		btnTor.setFocusPainted(false);
		btnTor.setContentAreaFilled(false);
		pnBtn.add(btnTor);
		showTorricelli();
		
		//boton stop
		btnStop = new JButton();
		btnStop.setIcon(new ImageIcon(getClass().getResource("img_game/stop.png")));
		btnStop.setFocusPainted(false);
		btnStop.setContentAreaFilled(false);
		pnBtn.add(btnStop);
        stopGame();
		
		//boton reset
		btnReset = new JButton();
		btnReset.setIcon(new ImageIcon(getClass().getResource("img_game/reset.png")));
		btnReset.setFocusPainted(false);
		btnReset.setContentAreaFilled(false);
		pnBtn.add(btnReset);
		resetGame();
		
		//boton salir
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(getClass().getResource("img_game/close.png")));
		btnExit.setFocusPainted(false);
		btnExit.setContentAreaFilled(false);
		pnBtn.add(btnExit);
		exitGame();

		//boton para cambiar a la simulación de balance de masas
		btnBal = new JButton();
		btnBal.setIcon(new ImageIcon(getClass().getResource("img_game/bal.png")));
		btnBal.setFocusPainted(false);
		btnBal.setContentAreaFilled(false);
		pnBtn.add(btnBal);
		showBalance();

		jf.add(this);

        //establecemos los parametros del contenedor
		jf.pack();
		jf.setSize(1200, 600);
		jf.setResizable(false);
		Image img2 = new ImageIcon(getClass().getResource("img_game/game.png")).getImage();
		jf.setIconImage(img2);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	
		// iniciamos el ciclo del hilo que controlara la simulación
		while (true) {
			this.fillTank();
			this.repaint();
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

	// Un pequeño Frame para desplegar mensajes 
	public void boxMessage(String msg, String title){
		JFrame frame_msg = new JFrame();
		frame_msg.setLayout(new BorderLayout());

		JPanel pnTxt = new JPanel();
		pnTxt.setLayout(new BorderLayout());
		pnTxt.setBorder(new MatteBorder(40,0,40,0,Color.WHITE));
        pnTxt.setBackground(Color.LIGHT_GRAY);
		frame_msg.add(pnTxt, BorderLayout.CENTER);

		JLabel titleLabel = new JLabel("<HTML><CENTER>" + title + "</CENTER></HTML>");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Helvetica",Font.BOLD,15));
		titleLabel.setForeground(Color.RED);
        titleLabel.setBorder(new MatteBorder(20,25,0,25,Color.LIGHT_GRAY));
        pnTxt.add(titleLabel, BorderLayout.NORTH);

		JLabel txtLabel = new JLabel("<HTML><CENTER>" + msg + "</CENTER></HTML>");
        txtLabel.setHorizontalAlignment(JLabel.CENTER);
		txtLabel.setFont(new Font("Helvetica",Font.BOLD,15));
		txtLabel.setForeground(Color.BLACK);
        txtLabel.setBorder(new MatteBorder(0,25,5,25,Color.LIGHT_GRAY));
        pnTxt.add(txtLabel, BorderLayout.CENTER);

		frame_msg.pack();
		frame_msg.setSize(500, 400);
		frame_msg.setResizable(false);
		Image img2 = new ImageIcon(getClass().getResource("img_game/game.png")).getImage();
		frame_msg.setIconImage(img2);
		frame_msg.setLocationRelativeTo(null);
		frame_msg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame_msg.setVisible(true);
	}

	// Función para calcular el nivel del fluido en el tiempo t
	public double nivelFld_T(){
		double nivelF_ = (((flujo_i - flujo_s) * time) + volume_I) / areaTq_;
		if(torricelli){
			nivelF_ = nivelFld_TQ();
		}
		return nivelF_;
	}

	// Función para calcular la velocidad de salida del fluido en el tiempo t
	public double speedFld_T(){
		double speed_T = speedOf;
		if(torricelli){
			speed_T = Math.sqrt(2*g*nivelFld_TQ()/100);
		}
		return speed_T;
	}

	// Función para calcular la cantidad o caudal de salida del fluido por el orificio en el tiempo t
	public double caudalFld_S(){
		double nivelF_ = areaOf_*speedFld_T();
		return nivelF_;
	}

	// Función para calcular el tiempo que tarda el tanque en vaciarse completamente
	public double emptyTime_T(){
		double timeEmpty = -(volume_I)/(flujo_i - flujo_s);
		return timeEmpty;
	}

	// Función para iniciar la simulación
	private void fillTank() {
        if(StopPlay){
			if(torricelli){
				if(emptyTime_TQ() > time){
					clockView();
					time++;
				}else{
					timeTub++;
				}
			}else{
				if(nivelFld_T() >= 0 && nivelFld_T() < alturaTq + 2){
					clockView();
					time++;
				}else{
					timeTub++;
				}
			}
		}
	}

    //dibujamos la simulación en el plano
	@Override
	public void paint(Graphics g) {
		super.paint(g);

        // CREAMOS EL LIEZO DONDE DIBUJAREMOS LA SIMULACIÓN
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // movemos el el punto (0,0) a la ezquina inferior izquierda del panel
        g.translate(getWidth()*10/100, getHeight()*90/100);

		// Dibujamos el suelo
		g.setColor(Color.DARK_GRAY);
        g.fillRect(-90, 0, getWidth() + 20, 30);

		if(torricelli){

			// Invocamos la función para dibujar el tanque
        	drawTankTorricelli(g);
			// Invocamos la función para dibujar el liquido
        	drawFluidTorricelli(g);

		}else{

			// Invocamos la función para dibujar el tanque
        	drawTank(g);
			// Invocamos la función para dibujar el liquido
        	drawFluid(g);
			// Invocamos la función para dibujar la manguera
			drawHose(g);

		}

	}

    // dibujamos el tanque
    public void drawTank(Graphics g){

        // Dibujamos el contorno del tanque y establecemos los valores iniciales para la altura del tanque
        g.setColor(Color.YELLOW);
        g.fillRect(0, -10, (int) (areaTq), 10); // Suelo del tanque

        g.fillRect(0, (int) -(alturaTq), 10, (int) (alturaTq)); // Pared izquierda del tanque
        g.fillRect((int) (areaTq), (int) -(alturaTq), 10, (int) (alturaTq)); // Pared derecha del tanque
        g.fillRect(-20, (int) -(alturaTq), 20, 10); // Manija izquierda del tanque
        g.fillRect((int) (areaTq), (int) -(alturaTq), 30, 10); // Manija derecha del tanque

        // Dibujamos el agujero en el tanque (x, y, z, q) -> y controla la altura del agujero
        g.setColor(Color.BLACK);
        g.fillRect((int) (areaTq), (int) -(alturaOf) - (int) (areaOf), (int) (alturaOf) + (int) (areaOf), (int) (areaOf));
        
		// Tuberia para la salida del fluido
		g.setColor(Color.YELLOW);
        g.fillRect((int) (areaTq), (int) -(alturaOf) - (int) (areaOf) - 10, getWidth(), 10); 
        g.fillRect((int) (areaTq), (int) -(alturaOf), getWidth(), 10); 
        g.fillRect((int) (areaTq) + (int) (areaOf), (int) -(alturaOf) - (int) (areaOf), 10, (int) (areaOf));

    }

    // Dibujamos el fluido dentro del tanque
    public void drawFluid(Graphics g){

		g.setColor(Color.BLUE);

		// Dibujamos el fluido que sale de la manguera
		g.fillRect(((int) (areaTq/2)) - (int) areaTub, ((int) -(alturaTq)) - 30, (int) areaTub, ((int) (alturaTq)) + 20);
		
		// Fluido dentro del tanque (x, y, z, q) -> y ^ q controlan el nivel del fluido
		g.fillRect(10, ((int) -(nivelFld_T())), (int) (areaTq) - 10, ((int) (nivelFld_T())) - 10);
		
		// Fluido dentro del tanque que se desplaza hacia el orificio de salida 
		if(areaTub > areaOf){
			g.fillRect((int) (areaTq)/2, (int) -(alturaOf) - (int) (areaOf) + (int) timeTub, (int) (areaTq)/2 + (int) (areaOf) + (int) time + (int) timeTub, ((int) (areaOf)) - (int) timeTub);
			g.fillRect((int) (areaTq)/2, (int) -(areaOf) - 10, (int) (areaTq)/2 + (int) (areaOf) + (int) time + (int) timeTub, (int) (areaOf));
		}else{
			g.fillRect((int) (areaTq)/2, (int) -(alturaOf) - (int) (areaOf) + (int) timeTub, (int) (areaTq)/2 + (int) (areaOf) + (int) time + (int) timeTub, ((int) (areaOf)) - (int) timeTub);
			g.fillRect((int) (areaTq)/2, (int) -(areaTub) - 10, (int) (areaTq)/2 + (int) (areaOf) + (int) time + (int) timeTub, (int) (areaTub));
		}
		
    }

	// Dibujamos la manguera de donde proviene el fluido
	public void drawHose(Graphics g){

		g.setColor(Color.RED);
        g.fillRect(-70, ((int) -(alturaTq)) - 100, (int) (areaTq/2) + 70, 25);
        g.fillRect(-90, ((int) -(alturaTq)) - 80, 30, ((int) (alturaTq)) + 80);
		g.fillOval(-85, ((int) -(alturaTq)) - 100, 30, 30);
		// g.fillOval((int) (areaTq/2) - 15, ((int) -(alturaTq)) - 100, 30, 30);
        // g.fillRect((int) (areaTq/2) - 10, ((int) -(alturaTq)) - 80, 25, 40);
        // g.fillRect((int) (areaTq/2) - 15, ((int) -(alturaTq)) - 50, 35, 20);

		int [] vx1 = {(int) (areaTq/2), 10, (int) (areaTq)};
        int [] vy1 = {((int) -(alturaTq)) - 100, ((int) -(alturaTq)) - 30, ((int) -(alturaTq)) - 30};
        g.fillPolygon (vx1, vy1, 3);

	}

	// Dibujamos el tanque con sus parametros iniciales
	public void drawNew(){

		btnDraw.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(isFieldFill()){
					areaOf = Float.parseFloat(areaOrificio.getText());
					alturaTq = Float.parseFloat(alturaTanque.getText());
					areaTq = Float.parseFloat(areaTanque.getText());
					nivelFld = (Float.parseFloat(nivelFluido.getText()));
					speedOf = (Float.parseFloat(velocidadSalida.getText()));
					areaTub = (Float.parseFloat(areaPipe.getText()));
					speedTub = (Float.parseFloat(speedPipe.getText()));
					areaOf_ = Math.pow(areaOf/2, 2)*Math.PI;
					areaTq_ = Math.pow(areaTq/2, 2)*Math.PI;
					areaPipe_ = Math.pow(areaTub/2, 2)*Math.PI;
					volume_I = areaTq_*nivelFld;
					flujo_i = areaPipe_*speedTub;
					flujo_s = areaOf_*speedFld_T();
				}else{
					boxMessage("Debe rellenar todos los campos de las variables, para poder dibujar la simulacion.", "Advertencia !");
				}
			}
		});

	}

	// Restablecemos los campos de entrada
	public void deleteFields(){

		btnRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				areaOrificio.setText("");
				alturaTanque.setText("");
				areaTanque.setText("");
				nivelFluido.setText("");
				velocidadSalida.setText("");
				speedPipe.setText("");
				areaPipe.setText("");
			}
		});

	}
	
	//variables hora, minuto y segundo del cronometro
	public void clockView()
	{
		contSClock++;

		if (contSClock > 59)
		{
			contSClock = 0;
			contMClock++;
			ms = "0" + String.valueOf(contSClock);
			if (contMClock > 59)
			{
				contMClock = 0;
				contHClock++;
				ss = "0" + String.valueOf(contMClock);
				if (contHClock > 24)
				{
					contHClock = 0;
					mm = "0" + String.valueOf(contHClock);
				}
				else if (contMClock < 10)
				{
					mm = "0" + String.valueOf(contHClock);
				}
				else
				{
					mm = String.valueOf(contHClock);
				}
			}
			else if (contMClock < 10)
			{
				ss = "0" + String.valueOf(contMClock);
			}
			else
			{
				ss = String.valueOf(contMClock);
			}
		}
		else if (contSClock < 10)
		{
			ms = "0" + String.valueOf(contSClock);
		}
		else
		{
			ms = String.valueOf(contSClock);
		}
		clock.setText(mm + ":" + ss + ":" + ms + " (" + time + " s)");
		speed.setText(String.valueOf(formating.format(speedFld_T())) + " cm/s" + " (" + formating.format(speedFld_T()/100) + " m/s)");
		level.setText(String.valueOf(Math.round(nivelFld_T())) + " cm" + " (" + Math.round(nivelFld_T()/100) + " m)");
	}

    //iniciamos una nuevo juego
	public void newGame() {
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initSimulation();
			}
		});
	}

	// Iniciamos la simulación
	public void initSimulation(){
		if(isFieldFill()){
				StopPlay = true;
		}else{
			boxMessage("Debe rellenar todos los campos de las variables, para poder iniciar una simulacion.", "Advertencia !");
		}
	}

	public void showInfo(){
		btnInfo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(torricelli){
					boxMessage(
								"La velocidad de salida del agua a traves de un orificio en el fondo"
								+ " de un tanque lleno hasta una altura, es igual a la velocidad de un"
								+ " objeto que cae libremente desde la misma altura.",

								"Ley de Torricelli"
					);
				}else{
					boxMessage("Los sistemas hidraulicos"
							+ " son ampliamente utilizados en diversos" 
							+ " campos de la ingenieria y la industria debido" 
							+ " a su eficiencia y capacidad para transmitir" 
							+ " grandes fuerzas y energia utilizando fluidos confinados." 
							+ " Estos sistemas se basan en los principios de la hidraulica," 
							+ " una rama de la fisica que estudia el comportamiento" 
							+ " de los fluidos en reposo (hidrostatica) y en movimiento (hidrodinamica).",

							"Sistemas Hidraulicos"
					);
				}
			}
		});
	}

	//Comprobamos que los campos de texto esten llenos
	boolean isFieldFill(){
		if(areaOrificio.getText().toString().length() != 0 &&
			alturaTanque.getText().toString().length() != 0 &&
			areaTanque.getText().toString().length() != 0 &&
			nivelFluido.getText().toString().length() != 0 &&
			velocidadSalida.getText().toString().length() != 0 &&
			speedPipe.getText().toString().length() != 0 &&
			areaPipe.getText().toString().length() != 0){
				return true;
		}
		return false;
	}

    //Pausamos el la simulación
	public void stopGame() {
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StopPlay = false;
			}
		});
	}

	public void reseted(){
		StopPlay = false;
		g = 9.8;
		c = 0.6;
		speedTub = 20;
		areaTub = 20;
		areaPipe_ = Math.pow(areaTub/2, 2)*Math.PI;
		speedOf = 0;
		areaOf = 30;
		areaOf_ = Math.pow(areaOf/2, 2)*Math.PI;
		alturaTq = 200;
		areaTq = 200;
		areaTq_ = Math.pow(areaTq/2, 2)*Math.PI;
		nivelFld = 190;
		alturaOf = 10;
		volume_I = areaTq_*nivelFld;
		flujo_i = areaPipe_*speedTub;
		flujo_s = areaOf_*speedFld_T();
		time = 0;
		timeOf = 0;
		timeTub = 0;

		areaOrificio.setText("");
		alturaTanque.setText("");
		areaTanque.setText("");
		nivelFluido.setText("");
		velocidadSalida.setText("");
		speedPipe.setText("");
		areaPipe.setText("");

		velocidadSalida.setEditable(true);
		velocidadSalida.setEnabled(true);
		speedPipe.setEditable(true);
		speedPipe.setEnabled(true);
		areaPipe.setEditable(true);
		areaPipe.setEnabled(true);

		if(torricelli){
			velocidadSalida.setText("0");
			speedPipe.setText("0");
			areaPipe.setText("0");
			velocidadSalida.setEditable(false);
			velocidadSalida.setEnabled(false);
			speedPipe.setEditable(false);
			speedPipe.setEnabled(false);
			areaPipe.setEditable(false);
			areaPipe.setEnabled(false);
		}

		contSClock = 0;
    	contMClock = 0;
    	contHClock = 0;
		ms = "00";
		ss = "00";
		mm = "00";

		clock.setText(mm + ":" + ss + ":" + ms + " (" + time + " s)");
		speed.setText(String.valueOf(formating.format(0.00)) + " cm/s" + " (" + formating.format(0.00) + " m/s)");
		level.setText(String.valueOf(Math.round(0.00)) + " cm" + " (" + Math.round(0.00) + " m)");

	}

	//Reiniciamos la simulación
	public void resetGame() {
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StopPlay = false;
				reseted();
			}
		});
	}

	// Función para cambiar a la simulación de balance de masas
	public void showBalance(){
		btnBal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				torricelli = false;
				reseted();
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

	// --------------------------------------- TORRICELLI --------------------------------------------

	
    // dibujamos el tanque
    public void drawTankTorricelli(Graphics g){

        // Dibujamos el contorno del tanque y establecemos los valores iniciales para la altura del tanque
        g.setColor(Color.MAGENTA);
        g.fillRect(0, -10, (int) (areaTq), 10); // Suelo del tanque

        g.fillRect(0, (int) -(alturaTq), 10, (int) (alturaTq)); // Pared izquierda del tanque
        g.fillRect((int) (areaTq), (int) -(alturaTq), 10, (int) (alturaTq)); // Pared derecha del tanque
        g.fillRect(-20, (int) -(alturaTq), 20, 10); // Manija izquierda del tanque
        g.fillRect((int) (areaTq), (int) -(alturaTq), 30, 10); // Manija derecha del tanque

        // Dibujamos el agujero en el tanque (x, y, z, q) -> y controla la altura del agujero
        g.setColor(Color.BLACK);
        g.fillRect((int) (areaTq)/2 - 5, -10, (int) (areaOf) + 5, 40);
        
		// Tuberia para la salida del fluido
		g.setColor(Color.MAGENTA);
        g.fillRect((int) (areaTq)/2 - 10, -10, 10, 50); 
		g.fillRect((int) (areaTq)/2 + (int) (areaOf), -10, 10, 50); 
		g.fillRect((int) (areaTq)/2 - 10, 30, (int) (areaOf) + 20, 10); 

    }

    // Dibujamos el fluido dentro del tanque
    public void drawFluidTorricelli(Graphics g){

		g.setColor(Color.CYAN);

		// Fluido dentro del tanque (x, y, z, q) -> y ^ q controlan el nivel del fluido
		g.fillRect(10, ((int) -(nivelFld_TQ())), (int) (areaTq) - 10, ((int) (nivelFld_TQ())) - 10);
		
		// Fluido dentro del tanque que se desplaza hacia el orificio de salida 
		g.setColor(Color.CYAN);
        g.fillRect((int) (areaTq)/2, ((int) -(nivelFld_TQ() - timeTub)), (int) (areaOf), (int) (nivelFld) + 30);
		
    }

	// Función para calcular el nivel del fluido en el tiempo t
	public double nivelFld_TQ(){
		double flujo = c*(areaOf_/areaTq_)*Math.sqrt(2*g*100);
		double nivelF_ = Math.pow((Math.sqrt(nivelFld) - (flujo*time/2)), 2);
		return nivelF_;
	}

	// Función para calcular el tiempo de vaciado del tanque 
	public double emptyTime_TQ(){
		double flujo = c*(areaOf_/areaTq_)*Math.sqrt(2*g*100);
		double timeFull = (Math.sqrt(nivelFld)*2) / flujo;
		return Math.abs(timeFull);
	}

	// Función para cambiar a la simulación de torricelli
	public void showTorricelli(){
		btnTor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				torricelli = true;
				reseted();
			}
		});
	}
	
}

