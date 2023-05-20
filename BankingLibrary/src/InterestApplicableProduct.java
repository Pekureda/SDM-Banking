public interface InterestApplicableProduct {

    //void applyInterestToProduct

    double getProductPrincipal();
    double getProductRate();
    int getProductTime();
    int getProductCompoundFrequency();
    double getCurrent_deposit();
    int getDeposit_total_money_time();
    boolean isDeposit();//no i to chyba powoduje ze to jest chujowy design xd mozna zmienic w takim razie i nie robic tego tym interfejsem
    //tylko jakos inaczej , moze visitor?
}
