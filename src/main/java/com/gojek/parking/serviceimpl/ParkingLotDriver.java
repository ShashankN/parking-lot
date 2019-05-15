package com.gojek.parking.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.gojek.parking.exception.CommandNotFoundException;
import com.gojek.parking.exception.InvalidCommandException;

import static com.gojek.parking.constants.MessageConstants.*;

public class ParkingLotDriver {

	public static final void run(String[] args) {

		StringCommandProcessor processor = new StringCommandProcessor();
		Mode inputMode = Mode.getMode(args.length);
		BufferedReader reader = getReader(inputMode, args);
		
		try {
			while (reader.ready() || inputMode == Mode.INTERACTIVE) {
				String line = reader.readLine();
				if (inputMode == Mode.INTERACTIVE && line.equalsIgnoreCase("exit")) {
					break;
				}
				processor.validateCommand(line);
				processor.executeCommand(line);
			}
		} catch (IOException | InvalidCommandException | CommandNotFoundException e) {
			throw new IllegalArgumentException(e);
		} finally {
			closeReader(reader);
		}

	}

	private static void closeReader(BufferedReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static BufferedReader getReader(Mode inputMode, String[] args) {
		if (inputMode == Mode.INTERACTIVE) {
			return new BufferedReader(new InputStreamReader(System.in));
		} else {
			try {
				return new BufferedReader(new FileReader(new File(args[0])));
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException(String.format(FILE_READ_ERROR_MSG, args[1]));
			}
		}
	}

	private enum Mode {
		INTERACTIVE, FILE;

		private static Mode getMode(int argCount) {
			if (argCount == 0) {
				return INTERACTIVE;
			} else if (argCount == 1) {
				return FILE;
			} else {
				throw new IllegalArgumentException(String.format(INVALID_NO_OF_ARGS, argCount));
			}
		}
	}
}
