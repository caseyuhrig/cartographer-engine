package cartographer.engine;

import java.text.SimpleDateFormat;
import java.util.Date;

import nuberplex.common.lang.DateTime;
import nuberplex.common.util.IntegerUtils;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;


public class Version extends AbstractStorable<Version>
{
    private static final long serialVersionUID = 1L;

    private static StorableMetaData META = null;

    private Integer major = null;

    private Integer minor = null;

    private Integer build = null;

    private Long revision = null;


    public Version()
    {
        super();
    }


    public Version(final Integer major, final Integer minor, final Integer build, final Long revision)
    {
        this();
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.revision = revision;
    }


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "version";
            META.add(this, "major", "major");
            META.add(this, "minor", "minor");
            META.add(this, "build", "build");
            META.add(this, "revision", "revision");
        }
        return META;
    }


    public static Version generate()
    {
        final Date timestamp = DateTime.now().toJavaDate();
        final Integer year = IntegerUtils.INTEGER(new SimpleDateFormat("yyyy").format(timestamp), null);
        final Integer minute = IntegerUtils.INTEGER(new SimpleDateFormat("m").format(timestamp), null);
        final Integer dayInYear = IntegerUtils.INTEGER(new SimpleDateFormat("D").format(timestamp), null);
        final Integer hourInDay = IntegerUtils.INTEGER(new SimpleDateFormat("H").format(timestamp), null);
        final Integer major = 5;
        final Integer minor = year - 2019;
        final Integer build = dayInYear;
        final Long revision = hourInDay.longValue() * 60L + minute.longValue();
        final Version version = new Version();
        version.setMajor(major);
        version.setMinor(minor);
        version.setBuild(build);
        version.setRevision(revision);
        return version;
    }


    @Override
    public String toString()
    {
        return String.format("%d.%d.%d.%d", major, minor, build, revision);
    }


    public Integer getMajor()
    {
        return major;
    }


    public Version setMajor(final Integer major)
    {
        this.major = major;
        return this;
    }


    public Integer getMinor()
    {
        return minor;
    }


    public Version setMinor(final Integer minor)
    {
        this.minor = minor;
        return this;
    }


    public Integer getBuild()
    {
        return build;
    }


    public Version setBuild(final Integer build)
    {
        this.build = build;
        return this;
    }


    public Long getRevision()
    {
        return revision;
    }


    public Version setRevision(final Long revision)
    {
        this.revision = revision;
        return this;
    }
}
