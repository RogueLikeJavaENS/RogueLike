package entity.living;

import utils.Check;

/**
 * Abstract class managing the stats of any living entity
 * @author luca
 */

public abstract class AbstractStats{
    public int getLifePointTotal() {
        return lifePointTotal;
    }

    public int getLifePointActual() {
        return lifePointActual;
    }
    public void setLifePointActual(int lifePointActual) { this.lifePointActual = lifePointActual; }

    public void upgradeLifePointTotal(int modifier){
        Check.checkPositivity(modifier);
        this.lifePointTotal=getLifePointTotal()+modifier;
        recoverHp(modifier);
    }

    public void sufferDamage(int damage) {
        Check.checkPositivity(damage);
        this.lifePointActual = Math.max((getLifePointActual() - damage), 0);
    }

    public void recoverHp(int heal) {
        Check.checkPositivity(heal);
        this.lifePointActual = Math.min((getLifePointActual() + heal), getLifePointTotal());
    }

    public int getManaPointTotal() {
        return manaPointTotal;
    }

    public int getManaPointActual() {
        return manaPointActual;
    }

    public void upgradeManaPointTotal(int modifier){
        Check.checkPositivity(modifier);
        this.manaPointTotal=getManaPointTotal()+modifier;
        recoverMp(modifier);
    }

    public boolean consumeMp(int cost) {
        Check.checkPositivity(cost);
        if ((getManaPointActual()-cost)>=0){
            this.manaPointActual = getManaPointActual()-cost;
            return true;
        }
        else {
            return false;
        }
    }

    public void recoverMp(int recover) {
        Check.checkPositivity(recover);
        this.manaPointActual = Math.min((getManaPointActual() + recover), getManaPointTotal());
    }

    public int getRangeNatural() {
        return rangeNatural;
    }

    public int getRangeTotal() {
        return rangeTotal;
    }

    public void editRangeTotal(int modify){
        this.rangeTotal = Math.max((getRangeTotal() + modify), getRangeNatural());
    }

    public int getInitiativeNatural(){
        return initiativeNatural;
    }

    public int getInitiativeActual(){
        return initiativeActual;
    }

    public void upInitiativeNatural(int upgrade){
        Check.checkPositivity(upgrade);
        this.initiativeNatural=getInitiativeNatural()+upgrade;
        editInitiativeActual(upgrade);
    }

    public void editInitiativeActual(int modify){
        this.initiativeActual = Math.max((getInitiativeActual() + modify), getInitiativeNatural());
    }

    public int getDamageRaw() {
        return damageRaw;
    }

    public int getDamageTotal() {
        return damageTotal;
    }

    public void changeDamageRaw(int modifier) {
        Check.checkPositivity(modifier);
        this.damageRaw = getDamageRaw()+modifier;
        changeDamageTotal(modifier);
    }

    public void changeDamageTotal(int modifier) {
        this.damageTotal = Math.max((getDamageTotal() + modifier), getDamageRaw());
    }

    public int getArmorNatural() {
        return armorNatural;
    }

    public int getArmorTotal() {
        return armorTotal;
    }

    public void changeArmorNatural(int modifier) {
        Check.checkPositivity(modifier);
        this.armorNatural = getArmorNatural()+modifier;
        changeArmorTotal(modifier);
    }

    public void changeArmorTotal(int modifier) {
        this.armorTotal = Math.max((getArmorTotal() + modifier), getArmorNatural());
    }

    public int getMoneyCount() {
        return moneyCount;
    }

    public void gainMoney(int up) {
        Check.checkPositivity(up);
        this.moneyCount = getMoneyCount()+up;
    }

    public void spendMoney(int down) {
        this.moneyCount = Math.max((getMoneyCount()-down), 0);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level>=1){
            this.level=level;
        }
        else {
            throw new IllegalArgumentException("this level input need to be above or equal 1");
        }
    }

    private int lifePointTotal;
    private int lifePointActual;
    private int manaPointTotal;
    private int manaPointActual;
    private final int rangeNatural;
    private int rangeTotal;
    private int initiativeNatural;
    private int initiativeActual;
    private int damageRaw;
    private int damageTotal;
    private int armorNatural;
    private int armorTotal;
    private int moneyCount;
    private int level;



    public AbstractStats(int lifePoint, int manaPoint, int range, int initiative, int damage, int armor, int moneyCount, int level) {
        this.lifePointTotal = lifePoint;
        this.lifePointActual = lifePoint;
        this.manaPointTotal = manaPoint;
        this.manaPointActual = manaPoint;
        this.rangeNatural = range;
        this.rangeTotal = range;
        this.initiativeNatural = initiative;
        this.initiativeActual = initiative;
        this.damageRaw = damage;
        this.damageTotal = damage;
        this.armorNatural = armor;
        this.armorTotal = armor;
        this.moneyCount = moneyCount;
        this.level = level;
    }
}
