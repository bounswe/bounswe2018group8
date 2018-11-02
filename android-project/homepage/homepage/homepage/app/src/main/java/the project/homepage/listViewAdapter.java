package wolfsoft.toptalov;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 3/22/2016.
 */
public class listViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BeanClass> beanClassArrayList;





    public listViewAdapter(Context context, ArrayList<BeanClass> beanClassArrayList) {
        this.context = context;
        this.beanClassArrayList = beanClassArrayList;


    }

    @Override
    public int getCount() {
        return beanClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanClassArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHoder viewHoder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview, parent, false);

            viewHoder = new ViewHoder();


            viewHoder.image = (ImageView) convertView.findViewById(R.id.market);
            viewHoder.title = (TextView) convertView.findViewById(R.id.sport);



            convertView.setTag(viewHoder);

        } else {

            viewHoder = (ViewHoder) convertView.getTag();
        }

        //  NavigationItems rowItem = (NavigationItems) getItem(position);

        BeanClass beanClass = (BeanClass) getItem(position);

            viewHoder.title.setText(beanClass.getTitle());
        //viewHoder.description.setText(beanClass.getDescription());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), beanClass.getImage());
        viewHoder.image.setImageBitmap(getRoundedCornerBitmap(bitmap, 20));


//        viewHoder.plus.setImageResource(beanClass.getImage());
//        viewHoder.min.setImageResource(beanClass.getImage());
        //viewHoder.no.setText(beanClass.getNo());


        return convertView;

    }


    private class ViewHoder{

        ImageView image;
        TextView title;
        TextView description;


    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



}
