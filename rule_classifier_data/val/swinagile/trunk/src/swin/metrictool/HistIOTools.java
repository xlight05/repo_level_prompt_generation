package swin.metrictool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class contains several static methods used to write and read histograms to disk files.
 */
public class HistIOTools
{
	/**
	 * Use a BufferedInputStream wrapped around a FileInputStream to read the binary data from
	 * the given file.
	 * @param file File object from which to locate file.
	 */
	public static Histogram readFile(File file)
	{

		try
		{
			FileInputStream in = new FileInputStream(file);
			BufferedInputStream dis = new BufferedInputStream(in);

			int num_data = in.available();
			byte[] data = new byte[num_data];

			dis.read(data);
			dis.close();

			return HistIOTools.unpackHistogram(data);

		}
		catch (IOException ioe)
		{
			return null;
		}

	} // readFile

	/**
	 * Use a FileOutputStream wrapped inside a BufferedOutputStream, to write the byte array
	 * data to the given file.
	 * @param file File object for the file to receive the histogram data.
	 */
	public static boolean writeFile(File file, Histogram histogram)
	{

		byte[] data = HistIOTools.packHistogram(histogram);
		try
		{
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(data);
			out.flush();
			out.close();
		}
		catch (IOException ioe)
		{
			return false;
		}
		return true;
	} // writeFile

	/**
	 * Convert histogram data to a byte array for storage using a byte array stream.
	 * @param hist Histogram object containing data to be packed.
	 */
	public static byte[] packHistogram(Histogram hist)
	{
		// Create the byte array stream.
		ByteArrayOutputStream byte_out = new ByteArrayOutputStream();

		// Wrap it in a data stream to take advantage of its
		// methods for writing different type data.
		DataOutputStream data_out = new DataOutputStream(byte_out);

		// The write methods can throw IOExceptions so must set up
		// to catch them
		try
		{

			// First pack the title by writing its length and then
			// the title as a char array (2 bytes per char)
			int num_chars = hist.getTitle().length();
			data_out.writeInt(num_chars);
			data_out.writeChars(hist.getTitle());

			// Next pack the title by writing its length and then
			// the title as a char array (2 bytes per char)
			num_chars = hist.getXLabel().length();
			data_out.writeInt(num_chars);
			data_out.writeChars(hist.getXLabel());

			// Save the bin array
			int[] bins = hist.getBins();
			data_out.writeInt(bins.length);
			for (int element : bins)
				data_out.writeInt(element);

			// Save underflow value.
			data_out.writeInt(hist.getValue(-1));
			// Save overflow value.
			data_out.writeInt(hist.getValue(bins.length));

			// Write the lower and upper range values
			data_out.writeDouble(hist.getLo());
			data_out.writeDouble(hist.getHi());

			data_out.flush();
			data_out.close();

			// Obtain from the ByteArrayOutputStream the byte array
			// with all of the histogram data.
			return byte_out.toByteArray();

		}
		catch (IOException ioe)
		{
			return null;
		}
	} // packHistogram

	/** Re-build a histogram from a byte array. * */
	public static Histogram unpackHistogram(byte[] hist_data)
	{
		// Create byte array input stream from which to extract
		// values of different types.
		ByteArrayInputStream byte_in = new ByteArrayInputStream(hist_data);
		DataInputStream data_in = new DataInputStream(byte_in);

		// The read methods can throw IOExceptions so must set up
		// to catch them
		try
		{
			// First read the characters for the title
			int num_chars = data_in.readInt();
			char[] char_array = new char[num_chars];
			for (int i = 0; i < num_chars; i++)
				char_array[i] = data_in.readChar();

			String title = String.valueOf(char_array);

			// First read the characters for the horizontal label
			num_chars = data_in.readInt();
			char_array = new char[num_chars];
			for (int i = 0; i < num_chars; i++)
				char_array[i] = data_in.readChar();

			String x_label = String.valueOf(char_array);

			// Get the bin data
			int num_bins = data_in.readInt();
			int[] bins = new int[num_bins];
			for (int i = 0; i < num_bins; i++)
				bins[i] = data_in.readInt();

			int under_flows = data_in.readInt();
			int over_flows = data_in.readInt();

			double lo = data_in.readDouble();
			double hi = data_in.readDouble();

			// Create a histogram with
			Histogram hist = new Histogram(title, x_label, num_bins, lo, hi);
			// Pack the rest of the data.
			hist.pack(bins, under_flows, over_flows, lo, hi);

			return hist;

		}
		catch (IOException ioe)
		{
			return null;
		}
	} // unpackHistogram

	/**
	 * Use a ObjectOutputStream wrapped around a FileOutputStream, to write a Histogram (made
	 * Serializable) to the given file.
	 * @param file File object for the file to receive the histogram data.
	 */
	public static boolean writeSerialFile(File file, Histogram histogram)
	{

		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

			out.writeObject(histogram);
			out.flush();
			out.close();

		}
		catch (IOException ioe)
		{
			return false;
		}
		return true;

	} // writeSerialFile

	/**
	 * Use a ObjectInputStream wrapped around a FileInputStream to read the Histogram object
	 * (made Serializable from the given file.
	 * @param file File object from which to locate file.
	 */
	public static Histogram readSerialFile(File file)
	{
		try
		{
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream obj_in = new ObjectInputStream(in);

			Histogram hist = (Histogram) obj_in.readObject();
			obj_in.close();
			return hist;
		}
		catch (ClassNotFoundException notex)
		{
			return null;
		}
		catch (IOException ioe)
		{
			return null;
		}
	} // readSerialFile

} // class HistIOTools
