package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class SampleController implements Initializable {

	  @FXML
	    private TextField txturl;

	    @FXML
	    private Button loadbtn1;

	
	@FXML
	private AnchorPane main;

	@FXML
	private AnchorPane welcome;

	@FXML
	private MediaView mView;

	@FXML
	private Pane controls;
	
	@FXML
	private Pane vpane;

	@FXML
	private Slider slider;

	@FXML
	private Button stop;

	@FXML
	private Button prev10;

	@FXML
	private Button openbtn;

	@FXML
	private Button loadbtn;

	@FXML
	private TextField txtnm;

	@FXML
	private Button next10;

	@FXML
	private ToggleButton lplay;

	@FXML
	private ToggleButton cplay;

	@FXML
	private ToggleButton mute;

	@FXML
	private MenuItem open;

	@FXML
	private Label dur;

	@FXML
	private Label dur1;
	
	@FXML
    private Slider vslider;
	
	@FXML
    private HBox hbx;
	
	@FXML
	private ImageView iv;

	MediaPlayer mp;
	Double MAX, MIN;

	double speed = 1;
	Slider ts;
	Object r;
	File tmp = null;
	File fopen = null;
	String path;
	Double lblval;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		welcome.setVisible(true);

		controls.setVisible(false);
		hbx.setVisible(false);
		mView.setVisible(false);
		vpane.setVisible(false);
		vslider.setVisible(false);
		controls.setVisible(false);
		
		next10.setGraphic(new FontAwesome().create(FontAwesome.Glyph.FORWARD).size(30).color(Color.WHITE));
		prev10.setGraphic(new FontAwesome().create(FontAwesome.Glyph.BACKWARD).size(30).color(Color.WHITE));
		cplay.setGraphic(
				new FontAwesome().create(FontAwesome.Glyph.PAUSE).size(40).color(Color.WHITE));
		lplay.setGraphic(
				new FontAwesome().create(FontAwesome.Glyph.PAUSE).size(20).color(Color.WHITE));
		stop.setGraphic(new FontAwesome().create(FontAwesome.Glyph.STOP).size(20).color(Color.WHITE));
		Glyph f = new FontAwesome().create(FontAwesome.Glyph.VOLUME_UP);
		f.setTextFill(Color.WHITE);
		mute.setGraphic(f.size(23));
		
		 
		
		
		// TODO Auto-generated method stub

		openbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				open();
			}
		});
		loadbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub//file.touri.tostring
				load();
			}
		});
		
	}

	void open() {
		FileChooser fc = new FileChooser();

		// FileChooser fc = new FileChooser();

		/*
		 * FileChooser.ExtensionFilter txtFilter = new
		 * FileChooser.ExtensionFilter("TEXT Files (*.txt)", "*.txt");
		 * fc.getExtensionFilters().add(txtFilter); FileChooser.ExtensionFilter
		 * phpFilter = new FileChooser.ExtensionFilter("PHP Files (*.php)", "*.php");
		 * fc.getExtensionFilters().add(phpFilter); FileChooser.ExtensionFilter
		 * javaFilter = new FileChooser.ExtensionFilter("JAVA Files (*.java)",
		 * "*.java"); fc.getExtensionFilters().add(javaFilter);
		 * FileChooser.ExtensionFilter cssFilter = new
		 * FileChooser.ExtensionFilter("CSS Files (*.css)", "*.css");
		 * fc.getExtensionFilters().add(cssFilter); FileChooser.ExtensionFilter
		 * htmlFilter = new FileChooser.ExtensionFilter("HTML Files (*.html)",
		 * "*.html"); fc.getExtensionFilters().add(htmlFilter);
		 * 
		 * tmpfile(); String path = getpath(); fc.setInitialDirectory(new File(path));
		 */

		fopen = fc.showOpenDialog(Main.mainstage);
		txtnm.setText(fopen.getAbsolutePath());
	}

	void load() {
		
		if (fopen.toURI().toString() != null) {
			path = fopen.toURI().toString();
			createmplayer(path);
		} else {
			createmplayer(txtnm.getText());
		}
	}
	void createmplayer(String path) {
		welcome.setVisible(false);
		hbx.setVisible(true);
		mView.setVisible(true);
		
		Media media = new Media(path);
		mp = new MediaPlayer(media);
		mView.setMediaPlayer(mp);
		mView.setFitWidth(controls.getPrefWidth());
		System.out.println(slider.getValue());

		// left side nu label
		;

		//dur.textProperty().bind(Bindings.format("%.2f", slider.valueProperty()));
		dur.textProperty().bind(
			    Bindings.createStringBinding(() -> {
			            Duration time = mp.getCurrentTime();
			            return String.format("%4d:%02d:%04.1f",
			                (int) time.toHours(),
			                (int) time.toMinutes() % 60,
			                time.toSeconds() % 3600);
			        },
			        mp.currentTimeProperty()));
		// right side nu label
		slider.valueProperty().addListener((ov, n, n1) -> dur1.setText("/" + (MAX - n1.doubleValue() + MIN)));

		// working of slider
		mp.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
			if (!slider.isValueChanging()) {
				slider.setValue(newTime.toSeconds());
			}
		});
		// changin slider position
		/*slider.valueProperty().addListener((observable, oldValue, newValue) -> {
			mp.seek(Duration.seconds(slider.getValue()));
			
		})*/;
		slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasChanging, Boolean isNowChanging) {
				if (!isNowChanging) {
					mp.seek(Duration.seconds(slider.getValue()));
				}
			}
		});
		main.setOnMouseMoved(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				controls.setVisible(true);
			}
		});
		main.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				controls.setVisible(false);
			}
		});
		mp.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("End");
			}
		});
		mp.setOnError(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Error :" + mp.getError());
			}

		});
		mp.setOnReady(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Ready!");
				mp.setAutoPlay(true);
				double d = media.getDuration().toSeconds();
				slider.setMin(0);
				slider.setMax(d);
				MAX = slider.getMax();
				MIN = slider.getMin();
				System.out.println("end1");
				// mp.seek(seekTime);
			}
		});
		cplay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				mp.play();
				if (cplay.isSelected()) {
					mp.pause();
					lplay.setSelected(true);
					cplay.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLAY).size(35).color(Color.WHITE));
					lplay.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLAY).size(20).color(Color.WHITE));
					
				} else {
					mp.play();
					lplay.setSelected(false);
					cplay.setGraphic(
							new FontAwesome().create(FontAwesome.Glyph.PAUSE).size(40).color(Color.WHITE));
					lplay.setGraphic(
							new FontAwesome().create(FontAwesome.Glyph.PAUSE).size(20).color(Color.WHITE));
				}
			}
		});
		lplay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				mp.play();
				if (lplay.isSelected()) {
					mp.pause();
					cplay.setSelected(true);
					cplay.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLAY).size(35).color(Color.WHITE));
					lplay.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLAY).size(20).color(Color.WHITE));
					
				} else {
					mp.play();
					cplay.setSelected(false);
					cplay.setGraphic(
							new FontAwesome().create(FontAwesome.Glyph.PAUSE).size(40).color(Color.WHITE));
					lplay.setGraphic(
							new FontAwesome().create(FontAwesome.Glyph.PAUSE).size(20).color(Color.WHITE));
				}

			}
		});
		stop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				mp.stop();
				welcome.setVisible(true);
				hbx.setVisible(false);
				mView.setVisible(false);
				controls.setVisible(false);
				main.setOnMouseMoved(null);
				main.setOnMouseExited(null);
				cplay.setSelected(false);
				lplay.setSelected(false);
				cplay.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLAY).size(35).color(Color.WHITE));
				lplay.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLAY).size(20).color(Color.WHITE));
			}
		});
		next10.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// speed = speed + 0.5;
				// mp.setRate(speed);
				double ds = slider.getValue();
				System.out.println(" pela  :" + ds);
				ds = ds + 30;
				mp.seek(Duration.seconds(ds));
				slider.setValue(ds);
				System.out.println("pchi :" + ds);
			}
		});
		prev10.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				double ds = slider.getValue();
				ds = ds - 10;
				mp.seek(Duration.seconds(ds));
				slider.setValue(ds);
				System.out.println("next :" + ds);
			}
		});
		mute.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (mute.isSelected()) {
					
					vpane.setVisible(true);
					vslider.setVisible(true);
				} else {
					
					vpane.setVisible(false);
					vslider.setVisible(false);
				}
			}
		});
		
		vslider.setValue(mp.getVolume() * 100);
		vslider.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				mp.setVolume(vslider.getValue() / 100);
				int v=(int) vslider.getValue();
				//labvol.setText(v+"");
				System.out.println(v);
				
				vslider.setTooltip(new Tooltip(v+""));
				if(v==0)
				{
					Glyph f = new FontAwesome().create(FontAwesome.Glyph.VOLUME_OFF);
					f.setTextFill(Color.WHITE);
					mute.setGraphic(f.size(23));
				}
				else if (v<=50) {
					Glyph f = new FontAwesome().create(FontAwesome.Glyph.VOLUME_DOWN);
					f.setTextFill(Color.WHITE);
					mute.setGraphic(f.size(23));
				}
				else
				{
					Glyph f = new FontAwesome().create(FontAwesome.Glyph.VOLUME_UP);
					f.setTextFill(Color.WHITE);
					mute.setGraphic(f.size(23));
				}
				
				
			}
		});
	}

}
