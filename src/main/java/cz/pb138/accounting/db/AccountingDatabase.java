package cz.pb138.accounting.db;

import cz.pb138.accounting.db.impl.AccountingException;
import org.xml.sax.InputSource;

import java.util.List;

/**
 * Interface for manipulating with accounting records
 *
 * All records returned by database can either be
 *      editable - changes in record are reflected in database, stored after commit or dropped by rollback
 *      static - changes in record are independent of the database
 *
 * @author Tomas Madeja
 */
public interface AccountingDatabase {

    /**
     * Kills embedded database
     * @throws AccountingException contains error code associated to corresponding error
     */
    void killDatabase() throws AccountingException;

    /**
     * Detects whether collection was found
     * @return true if collection found, else false
     */
    boolean colFound();

    /**
     * Detecs whether Owner information is set within database
     * @return true if owner is set, false otherwise
     */
    boolean isOwnerSet();

    /**
     * Creates single revenue record
     * @return Empty accounting record with preset issuing and billing date, editable record
     */
    Record addRevenue();

    /**
     * Creates single expenditure record
     * @return Empty accounting record with preset issuing and billing date, editable record
     */
    Record addExpenditure();


    /**
     * Find all records of revenue and expenditure in the given year based on billing date
     * @param after lower bound date after which records exist, including the date
     * @param before upper bound date before which records exist, including the date
     * @return list of Records, static records
     * @throws AccountingException contains error code corresponding to error
     */
    List<Record> getRecordsBetweenBilling(String after, String before) throws AccountingException;

    /**
     * Find all records of revenue and expenditure in the given year based on issuing date
     * @param after lower bound date after which records exist, including the date
     * @param before upper bound date before which records exist, including the date
     * @return list of Records, static records
     * @throws AccountingException contains error code corresponding to error
     */
    List<Record> getRecordsBetweenIssuing(String after, String before) throws AccountingException;

    /**
     * Commits changes (added revenues and expenditures). If conflict in database arises, may require rollback
     * @throws AccountingException contains error code corresponding to error
     */
    void commitChanges() throws AccountingException;

    /**
     * Converts entire database into single XML file with {@code <root/>} as it's root node
     * @return database as InputSource
     * @throws AccountingException contains error code corresponding to error
     */
    InputSource dbAsInputSource()  throws AccountingException;

    /**
     * Converts entire database into single XML file with {@code <root/>} as it's root node
     * @return database as String
     * @throws AccountingException contains error code corresponding to error
     */
    String dbAsString() throws AccountingException;

    /**
     * Get information about owner of this app
     * @return Owner object representation of database owner, editable record
     */
    Owner getOwner();

    /**
     * Return database to the last commited state
     * @throws AccountingException contains error code corresponding to error
     */
    void rollback() throws AccountingException;

    /**
     * Find all records of revenue and expenditure in the given year based on issuing date
     * @param after lower bound date after which records exist, including the date
     * @param before upper bound date before which records exist, including the date
     * @return double representting losses
     * @throws AccountingException contains error code corresponding to error
     */
    double getLossesByIssuingDate(String after, String before) throws AccountingException;

    /**
     * Find all records of revenue and expenditure in the given year based on issuing date
     * @param after lower bound date after which records exist, including the date
     * @param before upper bound date before which records exist, including the date
     * @return double representting earnings
     * @throws AccountingException contains error code corresponding to error
     */
    double getEarningsByIssuingDate(String after, String before) throws AccountingException;

    /**
     * Find all records of revenue and expenditure in the given year based on billing date
     * @param after lower bound date after which records exist, including the date
     * @param before upper bound date before which records exist, including the date
     * @return double representting losses
     * @throws AccountingException contains error code corresponding to error
     */
    double getLossesByBillingDate(String after, String before) throws AccountingException;

    /**
     * Find all records of revenue and expenditure in the given year based on billing date
     * @param after lower bound date after which records exist, including the date
     * @param before upper bound date before which records exist, including the date
     * @return double representting earnings
     * @throws AccountingException contains error code corresponding to error
     */
    double getEarningsByBillingDate(String after, String before) throws AccountingException;

    /**
     * Returns content of revenues as xml doc in form of
     * {@code
     * <root>
     *     <pb138-accowner>
     *         <record></record>
     *     </pb138-accowner>
     *     <pb138-accearnings>
     *         <record></record>
     *     </pb138-accearnings>
     * </root>
     * }
     * @return String containing XML content
     * @throws AccountingException contains error code corresponding to error
     */
    String revenues() throws AccountingException;

    /**
     * Returns content of expenditures as xml doc in form of
     * {@code
     * <root>
     *     <pb138-accowner>
     *         <record></record>
     *     </pb138-accowner>
     *     <pb138-expenses>
     *         <record></record>
     *     </pb138-expenses>
     * </root>
     * }
     * @return String containing XML content
     * @throws AccountingException contains error code corresponding to error
     */
    String expenditures() throws AccountingException;
}
