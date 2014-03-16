package runtime.graphics.paths;

/**
 * Created by user on 15.03.14.
 */
import java.util.ArrayList ;

import runtime.graphics.point.PSPoint ;
public class PSPath {
    private ArrayList<SequentialPath> sPath ;

    public PSPath(){
        sPath = new ArrayList<SequentialPath>() ;
    }

    public SequentialPath getLastSP(){
        if(sPath.size() == 0){
            return null ;
        }
        return sPath.get(sPath.size() - 1) ;
    }

    public PSPoint[] getBBox(){
        if(sPath == null){
            return null ;
        }
        PSPoint[] box = null ;
        for(SequentialPath sP : sPath){
            PSPoint[] newBox = sP.getBBox() ;
            if(box == null){
                box = newBox ;
            }
            else{
                if(newBox[0].getX() < box[0].getX())box[0].setX(newBox[0].getX());
                if(newBox[0].getY() < box[0].getY())box[0].setY(newBox[0].getY());
                if(newBox[1].getX() < box[1].getX())box[1].setX(newBox[1].getX());
                if(newBox[1].getY() < box[1].getY())box[1].setY(newBox[1].getY());
            }
        }
        return box ;
     }

    public void stroke(){
        //todo
    }

    public SequentialPath getLastSPath(){
        if(sPath.size() == 0){
            return null;
        }
        return sPath.get(sPath.size() - 1) ;
    }

    public void addSequentialPath(SequentialPath path){
        sPath.add(path) ;
    }

    public void addLineSegment(PSPoint begining, PSPoint end){
        if(sPath.size() == 0 || PSPoint.distance(getLastSP().getEnd(), begining ) > 0.0001) {
            SequentialPath newSPath = new SequentialPath(new LineSegment(begining, end)) ;
            sPath.add(newSPath) ;
            return ;
        }
        getLastSP().addPathSection(new LineSegment(begining, end));  ;
    }

    public void addArc(PSPoint currentPoint, PSPoint center, double radius, double angle1, double angle2, boolean clockwise){
        Arc arc = new Arc(center, radius, angle1, angle2, clockwise) ;
        if(currentPoint == null){
            if(sPath.size() == 0 || PSPoint.distance(sPath.get(sPath.size() - 1).getEnd(), arc.getBegin()) > 0.001){//distance - maybe it's undue
               SequentialPath newSPath = new SequentialPath(arc) ;              //because if Path != null then currentPoint != null
                return ;
            }
            getLastSP().addPathSection(arc); ;
        }
        else{
            addLineSegment(currentPoint,arc.getBegin()) ;
            getLastSP().addPathSection(arc); ;
        }
    }

    public void closePath(){
        for(SequentialPath spath : sPath){
            spath.close() ;
        }
    }

    public PSPath clone(){
        PSPath newPath = new PSPath() ;
        for( SequentialPath spath : sPath ){
            newPath.addSequentialPath(spath.clone()) ;
        }
        return newPath ;
    }
     public boolean isInside(PSPoint point){
        int count = 0 ;
        for(SequentialPath sp : sPath){
            count += sp.intersect(point);
        }
        return false ;
    }

}
