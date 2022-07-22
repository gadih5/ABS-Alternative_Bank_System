package bank;

import java.util.Comparator;

public class LoanDtoComperator implements Comparator<LoanDto>{
    @Override
    public int compare(LoanDto o1, LoanDto o2) {
        return o1.getLoanName().compareTo(o2.getLoanName());
    }
}
