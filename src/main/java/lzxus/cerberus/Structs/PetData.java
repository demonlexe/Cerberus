package lzxus.cerberus.Structs;

import lzxus.cerberus.Cerberus;
import lzxus.cerberus.Listeners.DogBehavior;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.*;

import java.util.Deque;
import java.util.LinkedList;

public class PetData {
    private Wolf w;
    private Deque<Entity> attackQueue;
    private static final String [] attackTypeList = {"a","p","m"};

    public PetData()
    {
        w = null;
        attackQueue = new LinkedList<Entity>();
    }
    public PetData(Wolf newWolf)
    {
        w = newWolf;
        attackQueue = new LinkedList<Entity>();
    }

    public Wolf getWolf()
    {
        return w;
    }
    public void setWolf(Wolf newWolf)
    {
        w = newWolf;
    }

    public Deque<Entity> getQueue(){
        return attackQueue;
    }

    public boolean checkQueueForEntity(Entity e)
    {
        if (attackQueue.contains(e))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void enQueue(Entity e)
    {
        if (isAllowedToAttack(w,e) && w.getTarget()!=e)
        {
            if (checkQueueForEntity(e))
            {
                attackQueue.remove(e);
            }
            while (attackQueue.size() > 20)
            {
                attackQueue.pollLast();
            }
            attackQueue.addLast(e);
            DogBehavior.attackChoice(this);
        }
    }

    public void enQueueFirst(Entity e)
    {
        if (isAllowedToAttack(w,e) && w.getTarget()!=e)
        {
            if (checkQueueForEntity(e))
            {
                attackQueue.remove(e);
            }

            while (attackQueue.size() > 20)
            {
                attackQueue.pollLast();
            }
            attackQueue.addFirst(e);
            DogBehavior.attackChoice(this);
        }
    }

    public Entity peekQueue()
    {
        if (attackQueue.peek() != null)
        {
            Entity toR = attackQueue.peekFirst();
            while (toR == null || toR.isDead()){
                if (attackQueue.isEmpty())
                    return null;
                toR = attackQueue.pollFirst();
            }
            return toR;
        }
        else
            return null;
    }

   /* public Entity deQueue()
    {
        if (attackQueue.peek() != null)
        {
            Entity toR = attackQueue.pollFirst();
            while (toR == null || toR.isDead()){
                if (attackQueue.isEmpty())
                    return null;
                toR = attackQueue.pollFirst();
            }
            return toR;
        }
        else
            return null;
    }*/

    //Static methods
    public static String [] getTypeList(){
        return attackTypeList;
    }


    public static boolean isAllowedToAttack(Wolf currentWolf, Entity e)
    {
        Player p = PlayerWolfData.isPlayerPet(currentWolf);
        if (p != null && !(e.equals(p)))
        {
            Integer attackAllowed = PlayerWolfData.getAttackStatus(p);
            if (attackAllowed != null && attackAllowed.equals(1))
            {
                String attackMode = PlayerWolfData.getAttackType(p);
                if (ArrayUtils.contains(attackTypeList,attackMode))
                {
                    if (attackMode.equals("m"))
                    {
                        if (e instanceof Monster)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("p"))
                    {
                        if (e instanceof Animals)
                        {
                            return true;
                        }
                    }
                    else if (attackMode.equals("a"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
