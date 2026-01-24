//@ts-check
import { test, expect, chromium } from '@playwright/test';
import UserAgent from 'user-agents';

test.use({
  storageState: 'myGearShareAuth.json'
});

test('city-based search', async ({ page }) => {

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
  await page.getByRole('button', { name: 'Mape' }).click();
  await page.locator('#countryOptions').selectOption('HR');
  await page.locator('#cityOptions').selectOption('10000');

  await page.waitForTimeout(2000);

  await browser.close();

});