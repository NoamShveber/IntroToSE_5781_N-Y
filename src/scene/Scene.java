package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point3D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Scene class represents a scene with a background, lights and geometries.
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public String name;

    /**
     * The background color of the scene.
     */
    public Color background;

    /**
     * The ambient color of the scene.
     */
    public AmbientLight ambientLight;

    /**
     * A list of all geometries in the scene.
     */
    public Geometries geometries;

    /**
     * A list of all lights in the scene.
     */
    public List<LightSource> lights;

    /**
     * Constructs a new scene with a given name.
     * Sets all colors as black and creates new empty lists for geometries and lights.
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
        background = new Color(Color.BLACK);
        ambientLight = new AmbientLight(new Color(Color.BLACK), 0);
        lights = new LinkedList<>();
    }

    /**
     * Set the scene's background color
     * @param background New color for the background
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set the scene's ambientLight
     * @param ambientLight New ambientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set the scene's geometry list.
     * Creates also an AABB tree for boundable geometries
     * @param geometries New list of geometries
     * @return this
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;

    }

    /**
     * Set the scene's light source list
     * @param lights New list of light sources
     * @return this
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * An xml parser for scene files saved as xml.
     * @param fileName input xml file
     * @throws ParserConfigurationException .
     * @throws IOException .
     * @throws SAXException .
     */
    public void sceneParser(String fileName) throws ParserConfigurationException, IOException, SAXException {

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(new File(fileName));

        //Normalize the XML Structure
        document.getDocumentElement().normalize();

        var root = document.getDocumentElement();

        // Parsing Background Color
        this.background = parseColor(root.getAttribute("background-color"));

        // Parsing Ambient Light
        var ambient = (Element)root.getChildNodes().item(1);
        this.ambientLight = new AmbientLight(parseColor(ambient.getAttribute("color")), 1);

        // Parsing geometries
        var geoLst = root.getChildNodes().item(3).getChildNodes();

        Geometries geometries = new Geometries();

        for (int i = 0; i < geoLst.getLength(); i++) {
            var geo = geoLst.item(i);
            if (geo.getNodeName().equals("triangle")) {
                var el = (Element)geo;
                Point3D p0 = parsePoint3D(el.getAttribute("p0"));
                Point3D p1 = parsePoint3D(el.getAttribute("p1"));
                Point3D p2 = parsePoint3D(el.getAttribute("p2"));

                geometries.add(new Triangle(p0, p1, p2));
            }

            if (geo.getNodeName().equals("sphere")) {
                var el = (Element)geo;
                Point3D center = parsePoint3D(el.getAttribute("center"));
                double radius = Integer.parseInt(el.getAttribute("radius"));

                geometries.add(new Sphere(radius, center));
            }

        }

        this.geometries = geometries;



    }

    /**
     * @param toParse The string to parse a point from.
     * @return The parsed point.
     */
    Point3D parsePoint3D(String toParse) {
        var parsed = toParse.split(" ");
        return new Point3D(Integer.parseInt(parsed[0]),
                Integer.parseInt(parsed[1]),
                Integer.parseInt(parsed[2]));

    }

    /**
     * @param toParse The string to parse a color from.
     * @return The parsed color.
     */
    Color parseColor(String toParse) {
        var parsed = toParse.split(" ");
        return new Color(Integer.parseInt(parsed[0]),
                Integer.parseInt(parsed[1]),
                Integer.parseInt(parsed[2]));

    }
}
