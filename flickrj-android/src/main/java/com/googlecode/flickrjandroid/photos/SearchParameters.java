/*
 * Copyright (c) 2005 Aetrion LLC.
 */
package com.googlecode.flickrjandroid.photos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.Parameter;
import com.googlecode.flickrjandroid.util.StringUtilities;

/**
 * @author Anthony Eden
 * @version $Id: SearchParameters.java,v 1.20 2009/07/23 20:41:03 x-mago Exp $
 */
public class SearchParameters {
    public static final long serialVersionUID = 12L;

    private String userId;
    private String groupId;
    private String woeId;
    private String media;
    private String contacts;
    private String[] tags;
    private String tagMode;
    private String text;
    private Date minUploadDate;
    private Date maxUploadDate;
    private Date minTakenDate;
    private Date maxTakenDate;
    private Date interestingnessDate;
    private String license;
    private Set<String> extras;
    private String[] bbox;
    private String placeId;
    private int accuracy = 0;
    private String safeSearch;
    private String[] machineTags;
    private String machineTagMode;
    private String latitude;
    private String longitude;
    private double radius = -1;
    private String radiusUnits;
    private boolean hasGeo = false;
    private boolean inCommons = false;

    public static final ThreadLocal<DateFormat> DATE_FORMATS = new ThreadLocal<DateFormat>() {
        protected synchronized DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static final ThreadLocal<DateFormat> MYSQL_DATE_FORMATS = new ThreadLocal<DateFormat>() {
        protected synchronized DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /** order argument */
    public static int DATE_POSTED_DESC = 0;
    /** order argument */
    public static int DATE_POSTED_ASC = 1;
    /** order argument */
    public static int DATE_TAKEN_DESC = 2;
    /** order argument */
    public static int DATE_TAKEN_ASC = 3;
    /** order argument */
    public static int INTERESTINGNESS_DESC = 4;
    /** order argument */
    public static int INTERESTINGNESS_ASC = 5;
    /** order argument */
    public static int RELEVANCE = 6;
    private int sort = 0;

    public SearchParameters() {

    }

    /**
     * Optional to use, if BBox is set.<p>
     * Defaults to maximum value if not specified.
     *
     * @param accuracy from 1 to 16
     * @see com.googlecode.flickrjandroid.Flickr#ACCURACY_WORLD
     * @see com.googlecode.flickrjandroid.Flickr#ACCURACY_COUNTRY
     * @see com.googlecode.flickrjandroid.Flickr#ACCURACY_REGION
     * @see com.googlecode.flickrjandroid.Flickr#ACCURACY_CITY
     * @see com.googlecode.flickrjandroid.Flickr#ACCURACY_STREET
     */
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getGroupId() {
        return groupId;
    }

    /**
     * The id of a group who's pool to search. If specified, only matching
     * photos posted to the group's pool will be returned.
     *
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Any photo that has been geotagged.<p>
     *
     * Geo queries require some sort of limiting agent in order to prevent
     * the database from crying. This is basically like the check against
     * "parameterless searches" for queries without a geo component.<p>
     *
     * A tag, for instance, is considered a limiting agent as are user
     * defined min_date_taken and min_date_upload parameters &emdash;
     * If no limiting factor is passed flickr will return only photos
     * added in the last 12 hours
     * (though flickr may extend the limit in the future).
     *
     * @param hasGeo
     */
    public void setHasGeo(boolean hasGeo) {
        this.hasGeo = hasGeo;
    }

    public boolean getHasGeo() {
        return hasGeo;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getTagMode() {
        return tagMode;
    }

    public void setTagMode(String tagMode) {
        this.tagMode = tagMode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getMinUploadDate() {
        return minUploadDate;
    }

    public void setMinUploadDate(Date minUploadDate) {
        this.minUploadDate = minUploadDate;
    }

    public Date getMaxUploadDate() {
        return maxUploadDate;
    }

    public void setMaxUploadDate(Date maxUploadDate) {
        this.maxUploadDate = maxUploadDate;
    }

    public Date getMinTakenDate() {
        return minTakenDate;
    }

    public void setMinTakenDate(Date minTakenDate) {
        this.minTakenDate = minTakenDate;
    }

    public Date getMaxTakenDate() {
        return maxTakenDate;
    }

    public void setMaxTakenDate(Date maxTakenDate) {
        this.maxTakenDate = maxTakenDate;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Date getInterestingnessDate() {
        return interestingnessDate;
    }

    /**
     * Set the date, for which interesting Photos to request.
     * 
     * @param intrestingnessDate
     */
    public void setInterestingnessDate(Date intrestingnessDate) {
        this.interestingnessDate = intrestingnessDate;
    }

    /**
     * Set the machine tags, for which Photos to request.
     * 
     * @param tags
     */
    public void setMachineTags(String[] tags) {
        this.machineTags = tags;
    }

    public String[] getMachineTags() {
        return machineTags;
    }
    /**
     * Set the machine tags search mode to use when requesting photos
     * 
     * @param tagMode
     */
    public void setMachineTagMode(String tagMode) {
        this.machineTagMode = tagMode;
    }

    public String getMachineTagMode() {
        return machineTagMode;
    }

    /**
     * List of extra information to fetch for each returned
     * record. Currently supported fields are:
     * license, date_upload, date_taken, owner_name,
     * icon_server, original_format, last_update, geo,
     * tags, machine_tags, o_dims, views, media, path_alias,
     * url_sq, url_t, url_s, url_m, url_l, url_o
     *
     * @param extras A set of extra-attributes
     * @see com.googlecode.flickrjandroid.photos.Extras#ALL_EXTRAS
     * @see com.googlecode.flickrjandroid.photos.Extras#MIN_EXTRAS
     */
    public void setExtras(Set<String> extras) {
        this.extras = extras;
    }

    /**
     * 4 values defining the Bounding Box of the area that 
     * will be searched.<p>
     * The 4 values represent the bottom-left corner of the box
     * and the top-right corner, minimum_longitude, minimum_latitude,
     * maximum_longitude, maximum_latitude.<p>
     *
     * Longitude has a range of -180 to 180,
     * latitude of -90 to 90. Defaults to -180,
     * -90, 180, 90 if not specified.<p>
     *
     * Unlike standard photo queries, geo (or bounding box)
     * queries will only return 250 results per page.<p>
     *
     * Geo queries require some sort of limiting agent in
     * order to prevent the database from crying.
     * This is basically like the check against "parameterless searches"
     * for queries without a geo component.<p>
     *
     * A tag, for instance, is considered a limiting agent as are
     * user defined min_date_taken and min_date_upload parameters.
     * If no limiting factor is passed flickr returns only photos
     * added in the last 12 hours (though flickr may extend the
     * limit in the future).
     *
     * @param minimum_longitude
     * @param minimum_latitude
     * @param maximum_longitude
     * @param maximum_latitude
     */
    public void setBBox(
        String minimum_longitude,
        String minimum_latitude,
        String maximum_longitude,
        String maximum_latitude
    ) {
        this.bbox = new String[] {
            minimum_longitude,
            minimum_latitude,
            maximum_longitude,
            maximum_latitude
        };
    }

    public String[] getBBox() {
        return bbox;
    }

    /**
     * Optional safe search setting.<br>
     * Un-authed calls can only see Safe content.
     *
     * @param level 1, 2 or 3
     * @see com.googlecode.flickrjandroid.Flickr#SAFETYLEVEL_SAFE
     * @see com.googlecode.flickrjandroid.Flickr#SAFETYLEVEL_MODERATE
     * @see com.googlecode.flickrjandroid.Flickr#SAFETYLEVEL_RESTRICTED
     */
    public void setSafeSearch(String level) {
        this.safeSearch = level;
    }

    public String getSafeSearch() {
        return safeSearch;
    }

    public int getSort() {
        return sort;
    }

    /**
     * Set the sort-order.<p>
     * The default is <a href="#DATE_POSTED_DESC">DATE_POSTED_DESC</a>
     *
     * @see com.googlecode.flickrjandroid.photos.SearchParameters#DATE_POSTED_ASC
     * @see com.googlecode.flickrjandroid.photos.SearchParameters#DATE_POSTED_DESC
     * @see com.googlecode.flickrjandroid.photos.SearchParameters#DATE_TAKEN_ASC
     * @see com.googlecode.flickrjandroid.photos.SearchParameters#DATE_TAKEN_DESC
     * @see com.googlecode.flickrjandroid.photos.SearchParameters#INTERESTINGNESS_ASC
     * @see com.googlecode.flickrjandroid.photos.SearchParameters#INTERESTINGNESS_DESC
     * @see com.googlecode.flickrjandroid.photos.SearchParameters#RELEVANCE
     * @param order
     */
    public void setSort(int order) {
        this.sort = order;
    }

    /**
     * @return A placeId
     * @see com.googlecode.flickrjandroid.places.PlacesInterface#resolvePlaceId(String)
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * PlaceId only used when bbox not set.
     * 
     * Geo queries require some sort of limiting agent in order to prevent
     * the database from crying. This is basically like the check against
     * "parameterless searches" for queries without a geo component.<p>
     *
     * A tag, for instance, is considered a limiting agent as are user defined
     * min_date_taken and min_date_upload parameters &emdash; If no limiting
     * factor is passed we return only photos added in the last 12 hours
     * (though we may extend the limit in the future).
     *
     * @param placeId
     * @see com.googlecode.flickrjandroid.places.PlacesInterface#resolvePlaceId(String)
     * @see com.googlecode.flickrjandroid.places.Place#getPlaceId()
     * @see com.googlecode.flickrjandroid.places.Location#getPlaceId()
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getWoeId() {
        return woeId;
    }

    /**
     * A Where on Earth identifier to use to filter photo clusters.<br>
     * For example all the photos clustered by locality in the United States (WOE ID 23424977).<br>
     * (not used if bbox argument is present).<p/>
     *
     * Geo queries require some sort of limiting agent in order to prevent
     * the database from crying. This is basically like the check against
     * "parameterless searches" for queries without a geo component.<p/>
     *
     * A tag, for instance, is considered a limiting agent as are user defined
     * min_date_taken and min_date_upload parameters.  If no limiting
     * factor is passed we return only photos added in the last 12 hours
     * (though flickr may extend the limit in the future).<p/>
     *
     * @param woeId
     * @see com.googlecode.flickrjandroid.places.Place#getWoeId()
     * @see com.googlecode.flickrjandroid.places.Location#getWoeId()
     */
    public void setWoeId(String woeId) {
        this.woeId = woeId;
    }

    public String getMedia() {
        return media;
    }

    /**
     * Filter results by media type. Possible values are all (default),
     * photos or videos.
     *
     * @param media
     */
    public void setMedia(String media) throws FlickrException {
        if (media.equals("all") ||
            media.equals("photos") ||
            media.equals("videos")
        ) {
            this.media = media;
        } else {
            throw new FlickrException("0", "Media type is not valid.");
        }
    }

    public String getContacts() {
        return contacts;
    }

    /**
     * Search your contacts. Valid arguments are either 'all' or 'ff'
     * for just friends and family.<p/>
     *
     * It requires that the "user_id" field also be set and allows you to limit
     * queries to only photos belonging to that user's photos. As in : All my
     * contacts photos tagged "aaron". (Experimental)
     *
     * @param contacts
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Collection<Parameter> getAsParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        
        if( inCommons ) {
        	parameters.add(new Parameter("is_commons", "true"));
        }

        String lat = getLatitude();
        if (lat != null) {
            parameters.add(new Parameter("lat", lat));
        }

        String lon = getLongitude();
        if (lon != null) {
            parameters.add(new Parameter("lon", lon));
        }

        double radius = getRadius();
        if (radius > 0) {
            parameters.add(new Parameter("radius", radius));
        }

        String radiusUnits = getRadiusUnits();
        if (radiusUnits != null) {
            parameters.add(new Parameter("radius_units", radiusUnits));
        }

        String media = getMedia();
        if (media != null) {
            parameters.add(new Parameter("media", media));
        }

        String userId = getUserId();
        if (userId != null) {
            parameters.add(new Parameter("user_id", userId));
            String contacts = getContacts();
            if (contacts != null) {
                parameters.add(new Parameter("contacts", contacts));
            }
        }

        String groupId = getGroupId();
        if (groupId != null) {
            parameters.add(new Parameter("group_id", groupId));
        }

        String[] tags = getTags();
        if (tags != null) {
            parameters.add(new Parameter("tags", StringUtilities.join(tags, ",")));
        }

        String tagMode = getTagMode();
        if (tagMode != null) {
            parameters.add(new Parameter("tag_mode", tagMode));
        }

        String[] mtags = getMachineTags();
        if (mtags != null) {
            parameters.add(new Parameter("machine_tags", StringUtilities.join(mtags, ",")));
        }

        String mtagMode = getMachineTagMode();
        if (mtagMode != null) {
            parameters.add(new Parameter("machine_tag_mode", mtagMode));
        }

        String text = getText();
        if (text != null) {
            parameters.add(new Parameter("text", text));
        }

        Date minUploadDate = getMinUploadDate();
        if (minUploadDate != null) {
            parameters.add(new Parameter("min_upload_date", new Long(minUploadDate.getTime() / 1000L)));
        }

        Date maxUploadDate = getMaxUploadDate();
        if (maxUploadDate != null) {
            parameters.add(new Parameter("max_upload_date", new Long(maxUploadDate.getTime() / 1000L)));
        }

        Date minTakenDate = getMinTakenDate();
        if (minTakenDate != null) {
            parameters.add(new Parameter("min_taken_date", ((DateFormat)MYSQL_DATE_FORMATS.get()).format(minTakenDate)));
        }

        Date maxTakenDate = getMaxTakenDate();
        if (maxTakenDate != null) {
            parameters.add(new Parameter("max_taken_date", ((DateFormat)MYSQL_DATE_FORMATS.get()).format(maxTakenDate)));
        }

        String license = getLicense();
        if (license != null) {
            parameters.add(new Parameter("license", license));
        }

        Date intrestingnessDate = getInterestingnessDate();
        if (intrestingnessDate != null) {
            parameters.add(new Parameter("date", ((DateFormat)DATE_FORMATS.get()).format(intrestingnessDate)));
        }

        String[] bbox = getBBox();
        if (bbox != null) {
            parameters.add(new Parameter("bbox", StringUtilities.join(bbox, ",")));
            if (accuracy > 0) {
                parameters.add(new Parameter("accuracy", accuracy));
            }
        } else {
            String woeId = getWoeId();
            if (woeId != null) {
                parameters.add(new Parameter("woe_id", woeId));
            }
        }

        String safeSearch = getSafeSearch();
        if (safeSearch != null) {
            parameters.add(new Parameter("safe_search", safeSearch));
        }

        boolean hasGeo = getHasGeo();
        if (hasGeo) {
            parameters.add(new Parameter("has_geo", "true"));
        }

        if (extras != null && !extras.isEmpty()) {
            parameters.add(new Parameter("extras", StringUtilities.join(extras, ",")));
        }

        if (sort != DATE_POSTED_DESC) {
            String sortArg = null;
            if(sort == DATE_POSTED_ASC) sortArg = "date-posted-asc";
            if(sort == DATE_TAKEN_DESC) sortArg = "date-taken-desc";
            if(sort == DATE_TAKEN_ASC) sortArg = "date-taken-asc";
            if(sort == INTERESTINGNESS_DESC) sortArg = "interestingness-desc";
            if(sort == INTERESTINGNESS_ASC) sortArg = "interestingness-asc";
            if(sort == RELEVANCE) sortArg = "relevance";
            if(sortArg != null) parameters.add(new Parameter("sort", sortArg));
        }

        return parameters;
    }

    public void setLatitude(String lat) {
        latitude = lat;
    }

    public void setRadius(double r) {
        radius = r;
    }

    public void setLongitude(String lon) {
        longitude = lon;
    }

    public void setRadiusUnits(String units) {
        radiusUnits = units;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }

    public String getRadiusUnits() {
        return radiusUnits;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public boolean isInCommons() {
		return inCommons;
	}

	public void setInCommons(boolean inCommons) {
		this.inCommons = inCommons;
	}

}
