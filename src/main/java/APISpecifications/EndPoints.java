package APISpecifications;

import utilities.Property;

public class EndPoints {
    //public static final String BASEURL = "https://mtn.lfs.local:30623/api/mtn/wallet";
    public static final String GETFRI = Property.getAuthProperty("mtn.GetFRIUrl");
    public static final String VERIFYFRI = Property.getAuthProperty("mtn.VerifyFRIUrl");
    public static final String WITHDRAWAL = Property.getAuthProperty("mtn.WithdrawalUrl");
}
