package runtime.graphics.paths;

import java.awt.*;
import java.util.ArrayList;
import runtime.graphics.point.PSPoint ;

/**
 * Created by user on 14.03.14.
 */
public class SequentialPath {
    private ArrayList<PathSection> paths = new ArrayList<PathSection>() ;

    public SequentialPath(){

    }

    public SequentialPath(PathSection firstPath){
        paths.add(firstPath) ;
    }

    public void addPathSection(PathSection ps){
        paths.add(ps) ;
    }

    public ArrayList<PathSection> getPaths (){
        return paths ;
    }

    public PSPoint getBegining(){
        if(paths.size() == 0){
            return null ;
        }
        return paths.get(0).getBegin() ;
    }

    public PSPoint getEnd(){
        if(paths.size() == 0){
            return null ;
        }
        return paths.get(paths.size() - 1).getEnd() ;
    }

    public PSPoint[] getBBox(){
        if(paths == null){
            return null ;
        }
        PSPoint[] box = null ;
        for(PathSection pSection : paths){
            PSPoint[] newBox = pSection.getBBox() ;
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

    public void close(){
        PSPoint pBegin = paths.get(0).getBegin() ;
        PSPoint pEnd   = paths.get(paths.size() - 1).getEnd() ;
        if(Point.distance(pBegin.getX(), pBegin.getY(), pEnd.getX(), pEnd.getY() ) > 0.00001 ){
            paths.add(new LineSegment(pBegin, pEnd)) ;
        }
    }
    public SequentialPath clone(){
        SequentialPath newPath = new SequentialPath();
        for(PathSection ps : paths){
            addPathSection(ps.clone());
        }
        return newPath ;
    }



    public int intersect(PSPoint point){
        int ans = 0 ;
        for(PathSection ps : paths){
            ans += ps.rayIntersect(point) ;
        }
        return ans ;
    }
}
