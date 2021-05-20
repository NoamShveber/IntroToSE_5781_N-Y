package elements;


import geometries.*;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import primitives.*;
import renderer.*;
import scene.Scene;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class RenderTests {
	private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setDistance(100) //
			.setViewPlaneSize(500, 500);

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
																													// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
																													// right
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
																														// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
																													// right

		ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() throws IOException, SAXException, ParserConfigurationException {
		Scene scene = new Scene("XML Test scene");

		sceneParser(scene, "basicRenderTestTwoColors.xml");

		ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	// For stage 6 - please disregard in stage 5
	/**
	 * Produce a scene with basic 3D model - including individual lights of the bodies 
	 * and render it into a png image with a grid
	 */


	@Test
	public void basicRenderMultiColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //

		scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50) //
						.setEmission(new Color(java.awt.Color.CYAN)), //
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
						.setEmission(new Color(java.awt.Color.GREEN)),
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
						.setEmission(new Color(java.awt.Color.RED)),
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
						.setEmission(new Color(java.awt.Color.BLUE)));

		ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.WHITE));
		render.writeToImage();
	}

	/**
	 * An xml parser for scene files saved as xml.
	 * @param scene the scene being created
	 * @param fileName input xml file
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	void sceneParser(Scene scene, String fileName) throws ParserConfigurationException, IOException, SAXException {

			//Get Document Builder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			//Build Document
			Document document = builder.parse(new File(fileName));

			//Normalize the XML Structure
			document.getDocumentElement().normalize();

			var root = document.getDocumentElement();

			// Parsing Background Color
			scene.setBackground(parseColor(root.getAttribute("background-color")));

			// Parsing Ambient Light
			var ambient = (Element)root.getChildNodes().item(1);
			scene.setAmbientLight(new AmbientLight(parseColor(ambient.getAttribute("color")), 1));

			// Parsing geometries
			var geoLst = root.getChildNodes().item(3).getChildNodes();

			Geometries geometries = new Geometries();

			for (int i = 0; i < geoLst.getLength(); i++) {
				var geo = geoLst.item(i);
				if (geo.getNodeName() == "triangle") {
					var el = (Element)geo;
					Point3D p0 = parsePoint3D(el.getAttribute("p0"));
					Point3D p1 = parsePoint3D(el.getAttribute("p1"));
					Point3D p2 = parsePoint3D(el.getAttribute("p2"));

					geometries.add(new Triangle(p0, p1, p2));
				}

				if (geo.getNodeName() == "sphere") {
					var el = (Element)geo;
					Point3D center = parsePoint3D(el.getAttribute("center"));
					double radius = Integer.parseInt(el.getAttribute("radius"));

					geometries.add(new Sphere(center, radius));
				}

			}

			scene.setGeometries(geometries);



	}

	Point3D parsePoint3D(String toParse) {
		var parsed = toParse.split(" ");
		return new Point3D(Integer.parseInt(parsed[0]),
				Integer.parseInt(parsed[1]),
				Integer.parseInt(parsed[2]));

	}

	Color parseColor(String toParse) {
		var parsed = toParse.split(" ");
		return new Color(Integer.parseInt(parsed[0]),
				Integer.parseInt(parsed[1]),
				Integer.parseInt(parsed[2]));

	}
}
