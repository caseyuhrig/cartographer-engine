package cartographer.engine;

import java.sql.Timestamp;

import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;


//import com.sun.jna.platform.win32.Advapi32;
//import com.sun.jna.platform.win32.Kernel32;
//import com.sun.jna.platform.win32.WinBase;
//import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
/**
 * @author casey
 */
public class UserProfile extends AbstractStorable<Data>
{
    private static final long serialVersionUID = 1L;

    // private static Logger LOG = LogManager.getLogger(UserProfile.class);
    private static UserProfile CURRENT = null;

    private Long customerID = null;

    private String username = null;

    private String password = null;

    private Timestamp lastLogin = null;

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "user_profile";
            META.add(this, "customer_id", "customerID");
            META.add(this, "username", "username");
            META.add(this, "password", "password");
            META.add(this, "last_login", "lastLogin");
        }
        return META;
    }


    public static UserProfile CURRENT()
    {
        if (CURRENT == null)
        {
            CURRENT = new UserProfile().load("CARTOGRAPHER", "cartographer");
        }
        return CURRENT;
    }


    public Boolean authenticate()
    {
        // final HANDLEByReference phUser = new HANDLEByReference()
        // if(! Advapi32.INSTANCE.LogonUser("administrator",
        // InetAddress.getLocalHost().getHostName(),
        // "password", WinBase.LOGON32_LOGON_NETWORK, WinBase.LOGON32_PROVIDER_DEFAULT, phUser))
        // {
        // throw new LastErrorException(Kernel32.INSTANCE.GetLastError());
        // }
        return true;
    }


    public UserProfile load(final String customerIdentifier, final String username)
    {
        Store.POPULATE(this,
                       "user_profile.*",
                       "customer, user_profile",
                       "user_profile.customer_id = customer.id AND customer.identifier = ? AND user_profile.username = ?",
                       customerIdentifier,
                       username);
        return this;
    }


    public Long getCustomerID()
    {
        return customerID;
    }


    public UserProfile setCustomerID(final Long customerID)
    {
        this.customerID = customerID;
        return this;
    }


    public String getUsername()
    {
        return username;
    }


    public UserProfile setUsername(final String username)
    {
        this.username = username;
        return this;
    }


    public String getPassword()
    {
        return password;
    }


    public UserProfile setPassword(final String password)
    {
        this.password = password;
        return this;
    }


    public Timestamp getLastLogin()
    {
        return lastLogin;
    }


    public UserProfile setLastLogin(final Timestamp lastLogin)
    {
        this.lastLogin = lastLogin;
        return this;
    }
}
