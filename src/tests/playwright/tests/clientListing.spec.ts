//@ts-check
import { test, expect, chromium } from '@playwright/test';
import UserAgent from 'user-agents';

test.use({
    storageState: 'myGearShareAuth.json'
});

test('client tries to post listing', async ({ page }) => {

    const browser = await chromium.launch();

    const optionsContext = {
        userAgent: new UserAgent([/Chrome/i, { deviceCategory: 'desktop' }]).userAgent,
        locale: 'en-US',
        viewport: { width: 1280, height: 720 },
        deviceScaleFactor: 1,
        recordVideo: {
            dir: 'videos/',
            size: { width: 1280, height: 720 },
        },
    };

    const context = await browser.newContext(optionsContext);
    await page.goto('http://localhost:5173/home');
    await page.getByRole('link', { name: 'Profil' }).click();
    await page.getByRole('button', { name: 'Postani Klijent' }).click();
    await page.getByRole('button', { name: 'Dodaj novi oglas' }).click();
    await page.getByRole('textbox', { name: 'Naziv proizvoda:' }).click();
    await page.getByRole('textbox', { name: 'Naziv proizvoda:' }).fill('naziv');
    await page.getByRole('textbox', { name: 'Naziv proizvoda:' }).press('Tab');
    await page.getByRole('textbox', { name: 'Opis proizvoda:' }).fill('opis');
    await page.getByRole('textbox', { name: 'Opis proizvoda:' }).press('Tab');
    await page.getByRole('spinbutton', { name: 'Najmanji mogući broj dana za' }).fill('1');
    await page.getByRole('spinbutton', { name: 'Najmanji mogući broj dana za' }).press('Tab');
    await page.getByRole('spinbutton', { name: 'Cijena:' }).fill('1');
    await page.getByRole('textbox', { name: 'Oglas vrijedi od:' }).fill('2026-01-24');
    await page.getByRole('textbox', { name: 'Oglas vrijedi do:' }).fill('2026-01-25');
    await page.getByRole('textbox', { name: 'Tip opreme:' }).click();
    await page.getByRole('textbox', { name: 'Tip opreme:' }).fill('oprema');
    await page.getByRole('textbox', { name: 'Tip opreme:' }).press('Tab');
    await page.getByRole('textbox', { name: 'Stanje opreme:' }).fill('stanje');
    await page.getByRole('textbox', { name: 'Ulica:' }).click();
    await page.getByRole('textbox', { name: 'Ulica:' }).fill('Maksimirska cesta');
    await page.getByRole('textbox', { name: 'Ulica:' }).press('Tab');
    await page.getByRole('textbox', { name: 'Kućni broj:' }).fill('88');
    await page.getByRole('textbox', { name: 'Kućni broj:' }).press('Tab');
    await page.getByRole('textbox', { name: 'Broj stana(neobavezno):' }).press('Tab');
    await page.getByRole('textbox', { name: 'Poštanski broj:' }).fill('10000');
    await page.getByRole('textbox', { name: 'Poštanski broj:' }).press('Tab');
    await page.getByRole('textbox', { name: 'Država:' }).fill('HR');
    page.once('dialog', dialog => {
        console.log(`Dialog message: ${dialog.message()}`);
        dialog.dismiss().catch(() => { });
    });
    await page.getByRole('button', { name: 'Kreiraj oglas' }).click();


    await page.waitForTimeout(2000);

    await browser.close();

});