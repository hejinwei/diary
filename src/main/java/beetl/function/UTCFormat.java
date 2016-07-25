package beetl.function;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.beetl.core.Context;
import org.beetl.core.Function;

public class UTCFormat implements Function {

    public Object call(Object[] args, Context ctx) {
        Long milliSeconds = Long.valueOf(args[0].toString());
        String pattern = args[1].toString();
        Date date = new Date(milliSeconds);
        try {
            ctx.byteWriter.write((new SimpleDateFormat(pattern).format(date)).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
