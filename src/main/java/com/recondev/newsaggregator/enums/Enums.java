package com.recondev.newsaggregator.enums;

public class Enums {

    public enum NewsFetchingStrategy {
        API, RSS, WEB_SCRAPING;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum Country {
        AE, AR, AT, AU, BE, BG, BR, CA, CH, CN, CO, CU, CZ, DE, EG, FR, GB, GR, HK, HU, ID, IE, IL, IN, IT, JP, KR, LT, LV, MA, MX, MY, NG, NL, NO, NZ, PH, PL, PT, RO, RS, RU, SA, SE, SG, SI, SK, TH, TR, TW, UA, US, VE, ZA;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum Language {
        AR, DE, EN, ES, FR, HE, IT, NL, NO, PT, RU, SV, UD, ZH;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }


    public enum Category {
        BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

}
