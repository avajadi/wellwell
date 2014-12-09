package se.skagelund.hallon.well;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WellMonitor extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private FormattingLabel<Double> volumeLabel = new FormattingLabel<>("%.2f m³");
	private FormattingLabel<Double> airTemperatureLabel = new FormattingLabel<>("%.1f°C");
	private FormattingLabel<Double> waterTemperatureLabel = new FormattingLabel<>("%.1f°C");
	private FormattingLabel<Integer> distanceLabel = new FormattingLabel<>("%d cm");
	private WellComunicator wellwell = new WellComunicator();

	public WellMonitor() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(buildDistancePane(), BorderLayout.LINE_START);
		add(buildVolumePane(), BorderLayout.CENTER);
		add(buildTemperaturPane(), BorderLayout.LINE_END);
		add(buildButtons(), BorderLayout.PAGE_END);
		setTitle("WellMonitor");
		setSize(500, 400);
		setVisible(true);
	}

	private Component buildVolumePane() {
		JPanel volume = new JPanel();
		volume.setLayout(new BoxLayout(volume, JFrame.EXIT_ON_CLOSE));
		volume.add(new JLabel("Volume"));
		volume.add(volumeLabel);
		return volume;
	}

	private Component buildTemperaturPane() {
		JPanel temps = new JPanel();
		temps.setLayout(new BoxLayout(temps, JFrame.EXIT_ON_CLOSE));

		temps.add(new JLabel("Temperature"));

		JPanel temp1 = new JPanel();
		temp1.add(new JLabel("Air:"));
		temp1.add(airTemperatureLabel);
		temps.add(temp1);

		JPanel temp2 = new JPanel();
		temp2.add(new JLabel("Water:"));
		temp2.add(waterTemperatureLabel);
		temps.add(temp2);

		return temps;
	}

	private Component buildDistancePane() {
		JPanel distance = new JPanel();
		distance.setLayout(new BoxLayout(distance, JFrame.EXIT_ON_CLOSE));
		distance.add(new JLabel("Distance"));
		distance.add(distanceLabel);
		return distance;
	}

	private Component buildButtons() {
		JPanel buttons = new JPanel();
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		buttons.add(refreshButton);

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new CloseActionListener());
		buttons.add(closeButton);
		return buttons;
	}

	public void actionPerformed(ActionEvent e) {
		refresh();
	}

	private void refresh() {
		int distance = wellwell.readDistance();
		distanceLabel.setValue(distance);
		volumeLabel.setValue(distanceToVolume(distance));
		airTemperatureLabel.setValue(wellwell.readAirTemperature());
		waterTemperatureLabel.setValue(wellwell.readWaterTemperature());
	}

	private Double distanceToVolume(int distance) {
		return (207-distance)*0.02545;
	}

	public class CloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private class FormattingLabel<V> extends JLabel {
		private static final long serialVersionUID = 1L;
		private String format;

		public FormattingLabel(String format) {
			super();
			setFormat(format);
		}

		public void setValue(V value) {
			setText(String.format(format,value));
		}

		public void setFormat(String format) {
			this.format = format;
		}
	}

}
