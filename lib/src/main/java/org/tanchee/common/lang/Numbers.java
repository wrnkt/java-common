package org.tanchee.common.lang;

public abstract class Numbers {

    private static final String[] ZERO_TO_TWENTY = {
        "zero", "one", "two", "three", "four", "five", 
        "six", "seven", "eight", "nine", "ten", 
        "eleven", "twelve", "thirteen", "fourteen", 
        "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"
    };

    private static final String[] MULTIPLES_OF_TEN = {
        "ten",
        "twenty",
        "thirty",
        "fourty",
        "fifty",
        "sixty",
        "seventy",
        "eighty",
        "ninety",
        "one-hundred",
    };

    protected static final String writtenNumberZeroToTwenty(int number) {
        if (number < 0 || number > 20)
            throw new IllegalArgumentException("number must be between 0 - 100");
        return ZERO_TO_TWENTY[number];
    }

    protected static final String writtenNumberZeroToHundred(int number) {
        if (number > 100)
            throw new IllegalArgumentException("number must be between 0 - 100");

        if (number <= 20) return writtenNumberZeroToTwenty(number);

        var tensPlace = number / 10;
        var partOne = MULTIPLES_OF_TEN[tensPlace-1];

        var onesPlace = number % 10;
        if (onesPlace == 0) return partOne;

        var partTwo = writtenNumberZeroToTwenty(onesPlace);

        return partOne + "-" + partTwo;
    }

    public static final String writtenNumber(int number) {
        if (number <= 100) return writtenNumberZeroToHundred(number);
        return "";
    }
    
}
