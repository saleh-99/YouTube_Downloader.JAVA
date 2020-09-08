

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YTD {
	private WebDriver driver;
	private WebDriverWait wait;
	private JFrame frm;
	private JPanel contentPane;
	private JList<String> con;
	DefaultListModel<String> demoList;
	private JTextArea songl;
	private JTextArea textArea;

	public static <V> void main(String[] args) {
		new YTD();

	}

	public YTD() {
		System.setProperty("webdriver.chrome.driver", "/Users/salehahmed/Downloads/chromedriver");
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless");

		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		
		tt();

		/*
		 * Scanner s; try { s = new Scanner(new
		 * File("/Users/salehahmed/Desktop/text.txt"));
		 * 
		 * while (s.hasNext()) {
		 * 
		 * String name = s.nextLine();
		 * 
		 * songl.setText(songl.getText()+" \n" +name);
		 * 
		 * } s.close(); } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block
		 * 
		 * }
		 */

	}

	public void tt() {
		frm = new JFrame();
		frm.setVisible(true);
		frm.setBackground(Color.GRAY);

		frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frm.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frm, "Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					driver.quit();
					System.exit(0);
				}
			}
		});
		frm.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frm.setContentPane(contentPane);

		JButton btn = new JButton("search");
		btn.setBounds(333, 243, 117, 29);
		contentPane.add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(songl.getText()!="") {
				new Thread(new Runnable() {
				     @Override
				     public void run() {
				    		for (String name : songl.getText().split("\\n")) {

								if (dd(driver, wait, name)) {
									demoList.addElement(name);
								}
								
							}
				     }
				}).start();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 200, 188);
		contentPane.add(scrollPane);

		songl = new JTextArea();
		songl.setText("");
		scrollPane.setViewportView(songl);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 206, 328, 66);
		contentPane.add(scrollPane_1);

		textArea = new JTextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setFont(new Font("Arial Hebrew", Font.ITALIC, 11));
		textArea.setForeground(new Color(204, 0, 51));
		textArea.setText("ss");
		scrollPane_1.setViewportView(textArea);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(274, 6, 170, 188);
		contentPane.add(scrollPane_2);

		demoList = new DefaultListModel<String>();
		con = new JList<String>(demoList);
		con.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		con.setLayoutOrientation(JList.VERTICAL_WRAP);
		con.setSelectedIndex(0);
		con.setBounds(434, 190, -137, -180);
		con.setVisible(true);
		scrollPane_2.setViewportView(con);

	}

	public boolean dd(WebDriver driver, WebDriverWait wait, String s) {
		boolean found = false;

		driver.navigate().to("https://www.youtube.com/");

		wait.until(presenceOfElementLocated(By.tagName("input")));
		WebElement input = driver.findElement(By.tagName("input"));
		input.clear();
		input.sendKeys(s);
		input.sendKeys(Keys.ENTER);

		wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"container\"]/ytd-two-column-search-results-renderer")));

		WebElement element = driver.findElement(By.xpath(
				"/html/body/ytd-app/div/ytd-page-manager/ytd-search/div[1]/ytd-two-column-search-results-renderer/div/ytd-section-list-renderer/div[2]/ytd-item-section-renderer/div[3]"));

		List<WebElement> elements = element.findElements(By.tagName("ytd-video-renderer"));
		String[] urls = new String[elements.size()];
		textArea.setText(textArea.getText() + " \n" + urls.length);

		for (int i = 0; i < urls.length; i++) {
			textArea.setText(textArea.getText() + " \n"
					+ elements.get(i).findElement(By.tagName("yt-formatted-string")).getText());

			urls[i] = elements.get(i).findElement(By.tagName("a")).getAttribute("href");

		}

		int i = 0;
		while (!found && i < urls.length) {

			String url = urls[i];
			url = "https://www.ss" + url.substring(12);
			driver.get(url);
			try {
				url = wait.until(presenceOfElementLocated(By.className("def-btn-box"))).findElement(By.tagName("a"))
						.getAttribute("href");

				Desktop.getDesktop().browse(new URI(url));
				
				found = true;
				textArea.setText(textArea.getText() + " \n" + "found " + url);
				
				//fileDownload(url,"/Users/salehahmed/Downloads");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				textArea.setText(textArea.getText() + " \n" + urls[i] + " can't dd");
				i++;
			}

		}
		return found;
	}

	final static int size = 1024;

	public static void fileUrl(String fAddress, String localFileName, String destinationDir) {
		OutputStream outStream = null;
		URLConnection uCon = null;

		InputStream is = null;
		try {
			URL Url;
			byte[] buf;
			int ByteRead, ByteWritten = 0;
			Url = new URL(fAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(destinationDir + "\\" + localFileName.substring(0, 3)));

			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((ByteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
			System.out.println("Downloaded Successfully.");
			System.out.println("File name:\"" + localFileName + "\"\nNo ofbytes :" + ByteWritten);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void fileDownload(String fAddress, String destinationDir) {

		int slashIndex = fAddress.lastIndexOf('/');
		int periodIndex = fAddress.lastIndexOf('.');

		String fileName = fAddress.substring(slashIndex + 1);

		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1) {
			fileUrl(fAddress, fileName, destinationDir);
		} else {
			System.err.println("path or file name.");
		}
	}



}