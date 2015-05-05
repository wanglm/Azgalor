package org.Azgalor.hadoop.entities;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * 自定义日志对象
 * 
 * @author ming
 *
 */
public class WapsWritable implements WritableComparable<WapsWritable> {
	private static final Log LOG = LogFactory.getLog(WapsWritable.class);
	private Text id;
	private Text udid;
	private LongWritable sum;//去重后的保存

	public WapsWritable() {
		set(new Text(),new Text(),new LongWritable(0L));
	}

	public WapsWritable(Text id, Text udid,LongWritable sum) {
		set(id,udid,sum);
	}
	public void set(Text id, Text udid,LongWritable sum) {
		this.id = id;
		this.udid = udid;
		this.sum=sum;
	}
	public Text getId() {
		return id;
	}

	public void setId(Text id) {
		this.id = id;
	}

	public Text getUdid() {
		return udid;
	}

	public void setUdid(Text udid) {
		this.udid = udid;
	}

	public LongWritable getSum() {
		return sum;
	}

	public void setSum(LongWritable sum) {
		this.sum = sum;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		id.write(out);
		udid.write(out);
		sum.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id.readFields(in);
		udid.readFields(in);
		sum.readFields(in);
	}

	@Override
	public int compareTo(WapsWritable o) {
		// 根据id来比较排序
		return id.compareTo(o.getId());
	}

	@Override
	public int hashCode() {
		// id的hash来分区
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// 根据id比较是否相同
		if (obj instanceof WapsWritable) {
			WapsWritable lw = (WapsWritable) obj;
			boolean isId=this.id.toString().equals(lw.getId().toString());
			boolean isUDID=this.udid.toString().equals(lw.getUdid().toString());
			LOG.info("id是否相等:---"+id.toString()+"~~~tmp的id="+lw.getId().toString()+"---"+isId);
			LOG.info("udid是否相等:---"+udid.toString()+"~~~tmp的udid="+lw.getUdid().toString()+"---"+isUDID);
			return isId && isUDID;
		} 
		return false;
	}

	@Override
	public String toString() {
		return "id=" + id.toString() + "  udid=" + udid.toString();
	}

}
