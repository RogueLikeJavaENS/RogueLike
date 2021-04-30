package entity.living;

import utils.Check;

/**
 * Abstract class managing the stats of any living entity
 * @author luca
 */

public abstract class AbstractStats{

    private int lifePointTotal;
    private int lifePointNatural;
    private int lifePointActual;

    private int manaPointTotal;
    private int manaPointNatural;
    private int manaPointActual;

    private final int rangeNatural;
    private int rangeTotal;

    private int initiativeNatural;
    private int initiativeTotal;

    private int damageNatural;
    private int damageTotal;

    private int armorNatural;
    private int armorTotal;

    private int moneyCount;
    private int level;


    public AbstractStats(int lifePoint, int manaPoint, int range, int initiative, int damage, int armor, int moneyCount, int level) {
        this.lifePointNatural = lifePoint;
        this.lifePointActual = lifePoint;
        this.manaPointNatural = manaPoint;
        this.manaPointActual = manaPoint;
        this.rangeNatural = range;
        this.rangeTotal = range;
        this.initiativeNatural = initiative;
        this.initiativeTotal = initiative;
        this.damageNatural = damage;
        this.damageTotal = damage;
        this.armorNatural = armor;
        this.armorTotal = armor;
        this.moneyCount = moneyCount;
        this.level = level;
    }

    public void changeLifePointTotal(int modifier) {
        this.lifePointTotal = Math.max((getArmorTotal() + modifier), getLifePointNatural());
    }

    public void upgradeLifePointNatural(int modifier){
        Check.checkPositivity(modifier);
        this.lifePointNatural = getLifePointNatural()+modifier;
        recoverHp(modifier);
    }

    public void sufferDamage(int damage) {
        Check.checkPositivity(damage);
        this.lifePointActual = Math.max((getLifePointActual() - damage), 0);
    }

    public void recoverHp(int heal) {
        Check.checkPositivity(heal);
        this.lifePointActual = Math.min((getLifePointActual() + heal), getLifePointNatural());
    }

    public void changeManaPointTotal(int modifier) {
        this.manaPointTotal = Math.max((getManaPointTotal() + modifier), getLifePointNatural());
    }

    public void upgradeManaPointNatural(int modifier) {
        Check.checkPositivity(modifier);
        this.manaPointNatural = getManaPointNatural()+modifier;
        recoverMp(modifier);
    }

    public boolean consumeMp(int cost) {
        Check.checkPositivity(cost);
        if ((getManaPointActual()-cost)>=0) {
            this.manaPointActual = getManaPointActual()-cost;
            return true;
        }
        else {
            return false;
        }
    }

    public void recoverMp(int recover) {
        Check.checkPositivity(recover);
        this.manaPointActual = Math.min((getManaPointActual() + recover), getManaPointNatural());
    }

    public void editRangeTotal(int modify) {
        this.rangeTotal = Math.max((getRangeTotal() + modify), getRangeNatural());
    }

    public void upInitiativeNatural(int upgrade) {
        Check.checkPositivity(upgrade);
        this.initiativeNatural=getInitiativeNatural()+upgrade;
        editInitiativeActual(upgrade);
    }

    public void editInitiativeActual(int modify) {
        this.initiativeTotal = Math.max((getInitiativeTotal() + modify), getInitiativeNatural());
    }

    public void changeDamageNatural(int modifier) {
        Check.checkPositivity(modifier);
        this.damageNatural = getDamageNatural()+modifier;
        changeDamageTotal(modifier);
    }

    public void changeDamageTotal(int modifier) {
        this.damageTotal = Math.max((getDamageTotal() + modifier), getDamageNatural());
    }

    public void changeArmorNatural(int modifier) {
        Check.checkPositivity(modifier);
        this.armorNatural = getArmorNatural()+modifier;
        changeArmorTotal(modifier);
    }

    public void changeArmorTotal(int modifier) {
        this.armorTotal = Math.max((getArmorTotal() + modifier), getArmorNatural());
    }

    public void setLevel(int level) {
        if (level>=1){
            this.level=level;
        }
        else {
            throw new IllegalArgumentException("this level input need to be above or equal 1");
        }
    }

    public void gainMoney(int up) {
        Check.checkPositivity(up);
        this.moneyCount = getMoneyCount()+up;
    }

    public void spendMoney(int down) {
        this.moneyCount = Math.max((getMoneyCount()-down), 0);
    }

    /* GETTERS */

    public int getDamageNatural() { return damageNatural; }
    public int getDamageTotal() { return damageTotal; }
    public int getLevel() { return level; }
    public int getMoneyCount() { return moneyCount; }
    public int getArmorNatural() { return armorNatural; }
    public int getArmorTotal() { return armorTotal; }
    public int getInitiativeNatural(){ return initiativeNatural; }
    public int getInitiativeTotal(){ return initiativeTotal; }
    public int getRangeNatural() { return rangeNatural; }
    public int getRangeTotal() { return rangeTotal; }
    public int getManaPointTotal() { return manaPointTotal; }
    public int getManaPointNatural() { return manaPointNatural; }
    public int getManaPointActual() { return manaPointActual; }
    public int getLifePointTotal() { return lifePointTotal; }
    public int getLifePointNatural() { return lifePointNatural; }
    public int getLifePointActual() { return lifePointActual; }

    /* SETTERS */
    public void setLifePointActual(int lifePointActual) { this.lifePointActual = lifePointActual; }

}
