package game.entity.living;

import utils.Check;

import java.util.Random;

/**
 * Abstract class managing the stats of any living game.entity
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

    private int agilityNatural;
    private int agilityTotal;

    private int damageNatural;
    private int damageTotal;

    private int armorNatural;
    private int armorTotal;

    private int moneyCount;
    private int level;


    public AbstractStats(int lifePoint, int manaPoint, int range, int agility, int damage, int armor, int moneyCount, int level) {
        this.lifePointNatural = lifePoint;
        this.lifePointActual = lifePoint;
        this.lifePointTotal = lifePoint;
        this.manaPointNatural = manaPoint;
        this.manaPointActual = manaPoint;
        this.manaPointTotal = manaPoint;
        this.rangeNatural = range;
        this.rangeTotal = range;
        this.agilityNatural = agility;
        this.agilityTotal = agility;
        this.damageNatural = damage;
        this.damageTotal = damage;
        this.armorNatural = armor;
        this.armorTotal = armor;
        this.moneyCount = moneyCount;
        this.level = level;
    }

    public void changeLifePointTotal(int modifier) {
        this.lifePointTotal = Math.max((getLifePointTotal() + modifier), getLifePointNatural());
    }

    public void upgradeLifePointNatural(int modifier){
        modifier = Check.checkPositivity(modifier);
        this.lifePointNatural = getLifePointNatural()+modifier;
        changeLifePointTotal(modifier);
        recoverHp(modifier);
    }

    public int sufferDamage(int damage) {
        damage = Check.checkPositivity(damage-armorTotal);
        this.lifePointActual = Check.checkPositivity(getLifePointActual() - damage);
        return damage;
    }

    public boolean hasAvoided() {
        Random gen = new Random();
        int hitRoll = gen.nextInt(100);
        return hitRoll<Math.min(2*agilityTotal, 30);
    }

    public void recoverHp(int heal) {
        heal = Check.checkPositivity(heal);
        this.lifePointActual = Math.min((getLifePointActual() + heal), getLifePointNatural());
    }

    public void changeManaPointTotal(int modifier) {
        this.manaPointTotal = Math.max((getManaPointTotal() + modifier), getManaPointNatural());
    }

    public void upgradeManaPointNatural(int modifier) {
        modifier = Check.checkPositivity(modifier);
        this.manaPointNatural = getManaPointNatural()+modifier;
        changeManaPointTotal(modifier);
        recoverMp(modifier);
    }

    public boolean consumeMp(int cost) {
        if (cost <= manaPointActual){
            manaPointActual = manaPointActual - cost;
            return true;
        }
        return false;
    }

    public void recoverMp(int recover) {
        recover = Check.checkPositivity(recover);
        this.manaPointActual = Math.min((getManaPointActual() + recover), getManaPointNatural());
    }

    public void editRangeTotal(int modify) {
        this.rangeTotal = Math.max((getRangeTotal() + modify), getRangeNatural());
    }

    public void upAgilityNatural(int upgrade) {
        upgrade = Check.checkPositivity(upgrade);
        this.agilityNatural = getAgilityNatural()+upgrade;
        editAgiltyActual(upgrade);
    }

    public void editAgiltyActual(int modify) {
        this.agilityTotal = Math.max((getAgilityTotal() + modify), getAgilityNatural());
    }

    public void changeDamageNatural(int modifier) {
        modifier = Check.checkPositivity(modifier);
        this.damageNatural = getDamageNatural()+modifier;
        changeDamageTotal(modifier);
    }

    public void changeDamageTotal(int modifier) {
        this.damageTotal = Math.max((getDamageTotal() + modifier), getDamageNatural());
    }

    public void changeArmorNatural(int modifier) {
        modifier = Check.checkPositivity(modifier);
        this.armorNatural = getArmorNatural()+modifier;
        changeArmorTotal(modifier);
    }

    public void changeArmorTotal(int modifier) {
        this.armorTotal = Math.max((getArmorTotal() + modifier), getArmorNatural());
    }

    public void setLevel(int level) {
        this.level = Math.max(level, 1);
    }

    public void gainMoney(int up) {
        up = Check.checkPositivity(up);
        this.moneyCount = getMoneyCount()+up;
    }

    public void spendMoney(int down) {
        down = Check.checkPositivity(down);
        moneyCount = Math.max((moneyCount - down), 0);
    }

    /* GETTERS */

    public int getDamageNatural() { return damageNatural; }
    public int getDamageTotal() { return damageTotal; }
    public int getLevel() { return level; }
    public int getMoneyCount() { return moneyCount; }
    public int getArmorNatural() { return armorNatural; }
    public int getArmorTotal() { return armorTotal; }
    public int getAgilityNatural(){ return agilityNatural; }
    public int getAgilityTotal(){ return agilityTotal; }
    public int getRangeNatural() { return rangeNatural; }
    public int getRangeTotal() { return rangeTotal; }
    public int getManaPointTotal() { return manaPointTotal; }
    public int getManaPointNatural() { return manaPointNatural; }
    public int getManaPointActual() { return manaPointActual; }
    public int getLifePointTotal() { return lifePointTotal; }
    public int getLifePointNatural() { return lifePointNatural; }
    public int getLifePointActual() { return lifePointActual; }
}
