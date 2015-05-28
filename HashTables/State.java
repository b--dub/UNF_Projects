package walsh_project5;

/**
 * The State class represents US States with several attributes, an accessor for
 * interrogating the state's name, and a toString() override for displaying the object as
 * a single record.
 *
 * @author Brad Walsh - N00150149
 */
public class State {

    private String name, capital, abbreviation, regionName;
    private int population, regionNumber;

    // String.format() template used when displaying records
    private static final String STATE_RECORD_FORMAT
            = "%1$-15s %2$-15s %3$-5s %4$,11d   %5$-15s %6$2d";

    /**
     * Constructor - Takes a String record formated in a predetermined fixed width format
     * and parses the values it holds into the appropriate fields.
     *
     * @param record String - a single record holding all the values for the State object
     */
    public State(String record) {
        // Delimit fields from the record based on fixed widths and trim white space
        this.name = record.substring(0, 15).trim();
        this.capital = record.substring(15, 30).trim();
        this.abbreviation = record.substring(30, 32).trim();
        this.population = Integer.parseInt(record.substring(32, 40).trim());
        this.regionName = record.substring(40, 55).trim();
        this.regionNumber = Integer.parseInt(record.substring(55, 56));
    }

    /**
     * Constructor - Takes a String[] array containing the instance variables for the
     * State object and parses them into the appropriate fields.
     *
     * @param fields String[] array holding each of the values associated w/a State object
     */
    public State(String[] fields) {
        super();
        this.name = fields[0];
        this.capital = fields[1];
        this.abbreviation = fields[2];
        this.population = Integer.parseInt(fields[3]);
        this.regionName = fields[4];
        this.regionNumber = Integer.parseInt(fields[5]);
    }

    /**
     * Getter method for the state name of the object
     *
     * @return the state's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the region number of the object
     *
     * @return the state's region #
     */
    public int getRegionNumber() {
        return regionNumber;
    }

    /**
     * Getter method for the population of the object
     *
     * @return the state's population
     */
    public int getPopulationSize() {
        return population;
    }

    /**
     * Returns a formatted State record in a single String
     *
     * @return String representation of the formated record
     */
    @Override
    public String toString() {
        return String.format(STATE_RECORD_FORMAT, name, capital, abbreviation, population,
                regionName, regionNumber);
    }
} // end class State

