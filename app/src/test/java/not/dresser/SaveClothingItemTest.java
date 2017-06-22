package not.dresser;


import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

import not.dresser.helpers.SaveClothingItem;

import static org.junit.Assert.assertEquals;


public class SaveClothingItemTest {

    @Test
    public void saveClothingItem() throws Exception {
        assertEquals(-1, new SaveClothingItem().saveButtonPressed("", "", "T-shirt", "festive", "winter"));
        assertEquals(-1, new SaveClothingItem().saveButtonPressed("nmn", "", "T-shirt", "festive", "winter"));
        assertEquals(-1, new SaveClothingItem().saveButtonPressed(null, "n", "T-shirt", "festive", "winter"));
    }
}
