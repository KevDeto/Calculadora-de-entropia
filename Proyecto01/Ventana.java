package Proyecto01;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTable tabla;
	private Calcular calcular;
	private DecimalFormat decimal;
	private JTextField textSolucion;

	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelSuperior = new JPanel();
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblTitulo = new JLabel("CALCULADORA DE ENTROPIA");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		panelSuperior.add(lblTitulo);

		JPanel panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(null);

		JLabel lblInstruccion = new JLabel("INGRESE UNA PALABRA/TEXTO:");
		lblInstruccion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInstruccion.setBounds(10, 23, 223, 14);
		panelCentral.add(lblInstruccion);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 988, 118);
		panelCentral.add(scrollPane);

		JTextArea txtArea = new JTextArea();
		txtArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtArea.setLineWrap(true);
		scrollPane.setViewportView(txtArea);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 226, 988, 229);
		panelCentral.add(scrollPane_1);

		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCalcular.setBounds(10, 177, 103, 38);
		panelCentral.add(btnCalcular);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLimpiar.setBounds(130, 177, 103, 38);
		panelCentral.add(btnLimpiar);

		JPanel panelInferior = new JPanel();
		panelCentral.add(panelInferior);
		panelInferior.setBounds(0, 463, 1008, 45);
		panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblSol = new JLabel("H(X) = ");
		lblSol.setFont(new Font("Tahoma", Font.BOLD, 21));
		panelInferior.add(lblSol);

		textSolucion = new JTextField();
		textSolucion.setHorizontalAlignment(SwingConstants.CENTER);
		textSolucion.setFont(new Font("Tahoma", Font.PLAIN, 21));
		textSolucion.setEditable(false);
		panelInferior.add(textSolucion);
		textSolucion.setColumns(10);

		this.decimal = new DecimalFormat("#.###");
		this.tabla = new JTable();
		scrollPane_1.setViewportView(tabla);
		// funciones
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcular = new Calcular(txtArea.getText());

				calcular.separarCadena();
				calcular.contarRepePorLetra();
				calcular.calcularProbabilidadSimbolo();
				calcular.calcularCantBitsPorSimbolo();
				calcular.calcularProbPorCantBits();
				calcular.calcularSumarResultado();

				tabla.setModel(cargarTabla(calcular));

				configurarAspectoTabla();

				textSolucion.setText(decimal.format(calcular.devolverSumaFinal()));
			}
		});

		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtArea.setText("");
			}
		});
	}

	// defino los nombres de las columnas y el contenido de cada celda
	private DefaultTableModel cargarTabla(Calcular cal) {
		DefaultTableModel tablaModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String[] nombresColumn = { "xi", "p(xi)", "Log2(p(xi)", "p(xi) * Log2(p(xi)" };
		tablaModel.setColumnIdentifiers(nombresColumn);

		int i = 0;
		Object[] datos;
		for (String letras : cal.devolverListaLetras()) {
			datos = contenidoCeldas(cal, i);
			Object[] contenido = { letras, datos[0], datos[1], datos[2] };
			tablaModel.addRow(contenido);
			i++;
		}
		return tablaModel;
	}

	private Object[] contenidoCeldas(Calcular cal, int i) {
		Double probSimbol = cal.devolverListaProbSimbolo().get(i);
		String dividir = String.valueOf(cal.devolverListaCantRep().get(i) + "/" + cal.devolverTexto().length()) + " =  "
				+ decimal.format(probSimbol);
		String cantBitSPorSim = "Log2(" + decimal.format(probSimbol) + ") =  "
				+ decimal.format(cal.devolverListaCantBitSimbolo().get(i));
		String solProbPorBits = decimal.format(cal.devolverListaSolProbPorBits().get(i));

		Object[] datos = { dividir, cantBitSPorSim, solProbPorBits };

		return datos;
	}

	private void configurarAspectoTabla() {
		// agranda la letra del header y deja el header fijo
		tabla.getTableHeader().setFont(new java.awt.Font("Dialog", 0, 16));
		tabla.getTableHeader().setResizingAllowed(false);
		tabla.getTableHeader().setReorderingAllowed(false);

		// para centrar los datos dentro de las celdas de la tabla
		DefaultTableCellRenderer tablaCell = new DefaultTableCellRenderer();
		tablaCell.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tablaCell);
		tabla.getColumnModel().getColumn(1).setCellRenderer(tablaCell);
		tabla.getColumnModel().getColumn(2).setCellRenderer(tablaCell);
		tabla.getColumnModel().getColumn(3).setCellRenderer(tablaCell);

		// para cambiar el alto de las celdas de toda la tabla
		tabla.setRowHeight(23);

		// para proporcinar un tamaño especifico a las columnas de la tabla
		TableColumnModel columnModel = tabla.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(0);
		columnModel.getColumn(1).setPreferredWidth(0);
		columnModel.getColumn(2).setPreferredWidth(120);
		columnModel.getColumn(3).setPreferredWidth(120);
	}
}
